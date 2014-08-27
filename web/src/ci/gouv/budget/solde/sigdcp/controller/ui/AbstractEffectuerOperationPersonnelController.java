package ci.gouv.budget.solde.sigdcp.controller.ui;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import javax.inject.Inject;

import lombok.Getter;
import lombok.Setter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;

import ci.gouv.budget.solde.sigdcp.model.AbstractModel;
import ci.gouv.budget.solde.sigdcp.model.Code;
import ci.gouv.budget.solde.sigdcp.model.dossier.BulletinLiquidation.AspectLiquide;
import ci.gouv.budget.solde.sigdcp.model.dossier.NatureDeplacement;
import ci.gouv.budget.solde.sigdcp.model.dossier.ValidationType;
import ci.gouv.budget.solde.sigdcp.model.traitement.NatureOperation;
import ci.gouv.budget.solde.sigdcp.model.traitement.NatureOperationDto;
import ci.gouv.budget.solde.sigdcp.model.traitement.Traitable;
import ci.gouv.budget.solde.sigdcp.model.traitement.Traitement;
import ci.gouv.budget.solde.sigdcp.service.DynamicEnumerationService;
import ci.gouv.budget.solde.sigdcp.service.dossier.NatureOperationService;

public abstract class AbstractEffectuerOperationPersonnelController<DATA extends AbstractModel<?> > extends AbstractEntityListUIController<DATA> implements Serializable {

	private static final long serialVersionUID = -2412073347414420827L;

	/*
	 * Services
	 */
	@Inject protected DynamicEnumerationService dynamicEnumerationService;
	@Inject protected NatureOperationService natureOperationService;
	
	@Getter protected NatureOperationDto natureOperationDto;
	
	/*
	 * Dto
	 */
	@Getter @Setter protected Boolean showMontant=Boolean.FALSE,showMontantPourcentage=Boolean.FALSE,showObservation=Boolean.FALSE,showTotalDepense=Boolean.FALSE,
			showValidation=Boolean.FALSE,showNature=Boolean.TRUE,accepterTraitementEnabled=false,showNumero=true,showNumero2=false,showDateCreation=Boolean.TRUE,
			showBudgetDisponible=Boolean.TRUE;
	
	@Getter protected BigDecimal totalDepense,disponible;
	@Getter protected Collection<SelectItem> items = new ArrayList<>();

	protected Boolean fixe = Boolean.FALSE,calculerDisponible=false;
	@Getter @Setter protected NatureDeplacement natureDeplacement;
	protected Collection<NatureDeplacement> natureDeplacementDisponibles,natureDeplacementSelectionnees = new ArrayList<>();
	
	@Getter @Setter protected Boolean showAspectLiquide;
	@Getter @Setter protected AspectLiquide aspectLiquide;
	@Getter @Setter protected Collection<AspectLiquide> aspectLiquides;
	@Getter @Setter protected Collection<SelectItem> montantPourcentages;
	
	protected abstract String natureOperationCode();

	protected abstract Collection<DATA> data(Collection<NatureDeplacement> natureDeplacements);
	
	public Traitement dernierTraitement(DATA data){
		return traitable(data).getDernierTraitement();
	}
	
	public abstract BigDecimal depense(DATA data);
	
	public abstract String numero(DATA data);
	
	public String numero2(DATA data){
		return null;
	}
	
	public String numero2Libelle(){
		return null;
	}
	
	public abstract Date dateCreation(DATA data);
	
	@SuppressWarnings("unchecked")
	public Traitable<? extends Traitement> traitable(DATA data){
		try {
			Traitable<? extends Traitement> t = (Traitable<? extends Traitement>) FieldUtils.readField(data, "traitable", true);
			return t.getDernierTraitement()==null?firstOperationTraitable(data):t;
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public Traitable<? extends Traitement> firstOperationTraitable(DATA data){
		return null;
	}
	
	protected abstract void valider(Collection<DATA> datas);
	
	@Override
	protected void initialisation() {
		String[] codes = defaultNatureDeplacmentCodeListe();
		if(codes!=null){
			fixe = Boolean.TRUE;
			natureDeplacementDisponibles = new ArrayList<>();
			for(String code : codes)
				natureDeplacementDisponibles.add(dynamicEnumerationService.findByClass(NatureDeplacement.class, code));
			
		}else{
			natureDeplacementDisponibles = dynamicEnumerationService.findAllByClass(NatureDeplacement.class);
			
		}
		
		if(Boolean.TRUE.equals(canShowAllNatureDeplacment())){
			items.add(new SelectItem(null, "Tout"));
		}else{
			natureDeplacement = dynamicEnumerationService.findByClass(NatureDeplacement.class, defaultNatureDeplacementCode());
		}
		
		for(NatureDeplacement nd : natureDeplacementDisponibles)
			items.add(new SelectItem(nd, nd.getLibelle()));
		natureOperationDto = natureOperationService.findDtoById(natureOperationCode());
		title = natureOperationDto.getNatureOperation().getLibelle();
		super.initialisation();	 
		defaultSubmitCommand.setValue(text("bouton.valider"));
		if(showPageHeader)
			enableSearch();
		defaultSubmitCommand.onSuccessGoBack();
		showObservation = showValidation = natureOperationDto.getConfigCount()>1;

		selected = (list==null||list.isEmpty())?null:list.get(0);
		
		calculerDisponible();
	}
	
	protected void calculerDisponible(){
		if(natureDeplacement==null || natureDeplacement.getCategorie().getDisponible()==null)
			disponible = null;
		if(natureDeplacement!=null &&  natureDeplacement.getCategorie().getDisponible()!=null){
			if(!calculerDisponible || !showPageHeader)
				disponible = natureDeplacement.getCategorie().getDisponible();
			else
				disponible = natureDeplacement.getCategorie().getDisponible().subtract(totalDepense);
		}
	}
	
	public void select(DATA choice){
		this.selected = choice;
	}
	
	public void appliquerTraitement(){
		
	}
	
	public void onValidationTypeChange(ValueChangeEvent valueChangeEvent){
		ValidationType validationType = (ValidationType) valueChangeEvent.getNewValue();
		if(validationType==null)
			return;
		switch(validationType){
		case ACCEPTER:break;
		default:
			RequestContext.getCurrentInstance().execute("traitementDialog.show();");
			break;
		}
	}
	
	@Override
	protected void onDefaultSubmitAction() throws Exception {
		valider(selectedMultiple);
	}
		
	protected String defaultNatureDeplacementCode(){
		return Code.NATURE_DEPLACEMENT_AFFECTATION;
	}
	
	protected String[] defaultNatureDeplacmentCodeListe(){
		return null;
	}
	
	protected Boolean canShowAllNatureDeplacment(){
		return Boolean.FALSE;
	}
	
	@Override
	protected List<DATA> load() {
		Collection<NatureDeplacement> natureDeplacements = null;
		if(Boolean.TRUE.equals(fixe) && natureDeplacement==null)
			natureDeplacements = natureDeplacementDisponibles;
		else{
			if(natureDeplacement!=null)
				natureDeplacements = Arrays.asList(natureDeplacement);
		}
		showNature=natureDeplacements.size()>1;
		Collection<DATA> collection = data(natureDeplacements);
		totalDepense = new BigDecimal(0);
		if(collection==null)
			return null;
		calculerTotalDepense(collection);
		return new ArrayList<>(collection);
	}
	
	protected void calculerTotalDepense(Collection<DATA> datas){
		totalDepense = new BigDecimal(0);
		if(datas==null)
			return;
		for(DATA data : datas){
			BigDecimal d = depense(data);
			if(d!=null)
				totalDepense = totalDepense.add(d);
		}
	}
	
	protected void calculerTotalDepense(){
		calculerTotalDepense(list);
	}
	
	protected String[] touteLesDepenses() {
		return new String[]{Code.NATURE_DEPLACEMENT_AFFECTATION,Code.NATURE_DEPLACEMENT_MUTATION,Code.NATURE_DEPLACEMENT_RETRAITE
				,Code.NATURE_DEPLACEMENT_OBSEQUE_FRAIS,Code.NATURE_DEPLACEMENT_TRANSIT_BAGAGGES_MAE,Code.NATURE_DEPLACEMENT_TRANSIT_BAGAGGES_STAGIAIRE,
				Code.NATURE_DEPLACEMENT_MISSION_HCI,Code.NATURE_DEPLACEMENT_TITRE_TRANSPORT_CONGE,Code.NATURE_DEPLACEMENT_TITRE_TRANSPORT_STAGE};
	}
	
	@Override
	protected void detailsOutcomeParameters(Map<String, List<String>> parameters, DATA object) {
		super.detailsOutcomeParameters(parameters, object);
		addParameters(parameters, webConstantResources.getRequestParamNatureOperation(), natureOperationCode());
	}
	
	@Override
	public String rowSeverity(DATA data) {
		Traitement traitement = dernierTraitement(data);
		if(traitement==null)
			return null;
		return StringUtils.isEmpty(traitement.getObservation())?null:CSS_SEVERITY_WARN;
	}
	
	@Override
	protected void onRemoveDetailsCommandAction(DATA object) throws Exception {
		super.onRemoveDetailsCommandAction(object);
		calculerTotalDepense();
	}
	
	public void ajouterLigne(){
		
	}
	
	public List<ValidationType> validationTypes(DATA data){
		return new ArrayList<>(natureOperationDto.getValidationTypes());
	}
	
	public void natureDeplacementChange(ValueChangeEvent valueChangeEvent){}
	
	public void aspetLiquideChange(ValueChangeEvent valueChangeEvent){}
	
	public Collection<SelectItem> validations(DATA data){
		NatureOperation natureOperation = genericService.findByClass(NatureOperation.class, natureOperationCode());
		Collection<SelectItem> items = new ArrayList<>();
		for(ValidationType v : validationTypes(data)){
			String name;
			switch(v){
			case ACCEPTER:name = natureOperation.getMessage();break;
			//case REJETER:name = "Non "+natureOperation.getMessage();break;
			default:name = v.getLibelle();break;
			}
			items.add(new SelectItem(v, name));
		}
			
		return items;
	}

	public void onRowSelect(SelectEvent event){
		System.out
				.println("AbstractEffectuerOperationPersonnelController.onRowSelectCheckbox() : "+event);
	}
	
	public void onRowUnselect(UnselectEvent  event){
		System.out
				.println("AbstractEffectuerOperationPersonnelController.onRowUnselectCheckbox() : "+event);
	}
}
