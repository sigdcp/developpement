package ci.gouv.budget.solde.sigdcp.controller.liquidation;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import ci.gouv.budget.solde.sigdcp.controller.dossier.AbstractBulletinLiquidationListePersonnelController;
import ci.gouv.budget.solde.sigdcp.model.Code;
import ci.gouv.budget.solde.sigdcp.model.dossier.BulletinLiquidation;

@Named @ViewScoped
public class MiseEnLiquidationDossierController extends AbstractBulletinLiquidationListePersonnelController implements Serializable {

	private static final long serialVersionUID = -2412073347414420827L;
	
	//private String typeDocument;
	
	@Override
	protected void initialisation() {
		/*typeDocument = Faces.getRequestParameter(webConstantResources.getRequestParamLiquidation());
		if(webConstantResources.getRequestParamLiquidationBulletin().equals(typeDocument)){
			aspectLiquide = AspectLiquide.DEMANDE;
		}else if(webConstantResources.getRequestParamLiquidationBonTransport().equals(typeDocument)){
			aspectLiquide = AspectLiquide.TITRE_TRANSPORT;
		}*/
		super.initialisation();
		//showBulletinNumero=Boolean.FALSE;
		//showMontant=true;
		//showNumero=true;
		showObservation=showValidation=false;
		showBudgetDisponible=false;
		listTitle="Liste des demandes";
		defaultSubmitCommand.setRendered(false);
		/*title = "Etablissement de ";
		if(webConstantResources.getRequestParamLiquidationBulletin().equals(typeDocument)){
			title += "bulletin de liquidation";
		}else if(webConstantResources.getRequestParamLiquidationBonTransport().equals(typeDocument)){
			title += "bon de transport";
			rechercherCommande=null;
		}*/
		/*
		aspectLiquides = Arrays.asList(AspectLiquide.values());
		montantPourcentages = new ArrayList<>();
		montantPourcentages.add(new SelectItem(new BigDecimal("0.2"),"20%"));
		montantPourcentages.add(new SelectItem(new BigDecimal("0.8"),"80%"));
		montantPourcentages.add(new SelectItem(new BigDecimal("1"),"100%"));
		*/
		//updateAspectLiquide(natureDeplacement);
		selectLabel="bouton.selectionner";
	}
	
	@Override
	protected String natureOperationCode() {
		return Code.NATURE_OPERATION_ETABLISSEMENT_BL;
	}
	
	@Override
	protected String[] defaultNatureDeplacmentCodeListe() {
		return new String[]{Code.NATURE_DEPLACEMENT_AFFECTATION,Code.NATURE_DEPLACEMENT_MUTATION,Code.NATURE_DEPLACEMENT_RETRAITE,Code.NATURE_DEPLACEMENT_MISSION_HCI};
	}
	
	
	
	@Override
	protected String objectOutcome(Object entity) {
		return "mettreenliquidationform";
	}
	
	@Override
	public Date dateCreation(BulletinLiquidation bulletinLiquidation) {
		return bulletinLiquidation.getDossier().getDateCreation();
	}
	
	@Override
	public String numero(BulletinLiquidation bulletinLiquidation) {
		return "MEF/DGBF/DS/"+bulletinLiquidation.getDossier().getDeplacement().getNature().getCode()+"/"+ bulletinLiquidation.getDossier().getId();
	}
	
	@Override
	protected void hrefParameters(Map<String, Object> parameters,BulletinLiquidation bulletinLiquidation) {
		super.hrefParameters(parameters, bulletinLiquidation);
		parameters.put(webConstantResources.getRequestParamDossier(), bulletinLiquidation.getDossier().getId());
	}
		
	/*	
	public void natureDeplacementChange(ValueChangeEvent valueChangeEvent){
		updateAspectLiquide((NatureDeplacement)valueChangeEvent.getNewValue());
		RequestContext.getCurrentInstance().update("form:crpg");
	}
	
	public void updateAspectLiquide(NatureDeplacement natureDeplacement){
		if(showAspectLiquide = natureDeplacement!=null && Code.NATURE_DEPLACEMENT_MISSION_HCI.equals( natureDeplacement.getCode() )){
			if(aspectLiquide==null)
				aspectLiquide = AspectLiquide.DEMANDE;
			showMontantPourcentage = AspectLiquide.DEMANDE.equals(aspectLiquide);
		}else{
			aspectLiquide = null;
			showMontantPourcentage = false;
		}
	}
	*/
	/*
	@Override
	public void aspetLiquideChange(ValueChangeEvent valueChangeEvent) {
		aspectLiquide = (AspectLiquide) valueChangeEvent.getNewValue();
		showMontantPourcentage = AspectLiquide.DEMANDE.equals(aspectLiquide);
	}
	
	@Override
	public Boolean getShowMontantPourcentage() {
		return AspectLiquide.DEMANDE.equals(aspectLiquide);
	}
	
	@Override
	protected void processLoaded(DossierDto dto) {
		if(getShowMontantPourcentage())
			dto.setMontantPourcentage(new BigDecimal("0.8"));
	}*/
	
	@Override
	protected ProcessingType getProcessingType() {
		return ProcessingType.SINGLE;
	}
		
}
