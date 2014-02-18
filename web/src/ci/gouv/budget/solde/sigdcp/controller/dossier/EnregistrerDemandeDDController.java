package ci.gouv.budget.solde.sigdcp.controller.dossier;


import java.io.Serializable;
import java.util.Date;

import javax.faces.event.ValueChangeEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import lombok.Getter;
import lombok.Setter;
import ci.gouv.budget.solde.sigdcp.model.Code;
import ci.gouv.budget.solde.sigdcp.model.dossier.DossierDD;
import ci.gouv.budget.solde.sigdcp.model.dossier.TypeDepense;
import ci.gouv.budget.solde.sigdcp.model.geographie.Localite;
import ci.gouv.budget.solde.sigdcp.model.identification.Grade;
import ci.gouv.budget.solde.sigdcp.model.identification.Section;
import ci.gouv.budget.solde.sigdcp.service.dossier.DossierDDService;
import ci.gouv.budget.solde.sigdcp.service.utils.validaton.AbstractDossierValidator;
import ci.gouv.budget.solde.sigdcp.service.utils.validaton.DossierDDValidator;
 
@Named @ViewScoped
public class EnregistrerDemandeDDController extends AbstractDossierUIController<DossierDD,DossierDDService> implements Serializable {
	
	private static final long serialVersionUID = -611561465509440427L;
	
	/*
	 * Services
	 */  
	@Inject private DossierDDService dossierDDService;
	
	@Getter @Setter private Boolean marie;
	@Getter @Setter private Integer nombreEnfant=0;
	
	@Inject private DossierDDValidator validator;
	
	/*
	 * Attributs de parametrages de la vue
	 */
	@Getter private Boolean showDatePriseService = Boolean.FALSE;
	@Getter private Boolean showDateCessationService = Boolean.FALSE;
	@Getter private Boolean showDateMiseRetraite = Boolean.FALSE;
	@Getter private Boolean showServiceOrigine = Boolean.FALSE;
	@Getter private Boolean showServiceAcceuil = Boolean.FALSE;
	
	@Override
	protected DossierDDService getDossierService() {
		return dossierDDService;
	}
	
	@Override
	protected AbstractDossierValidator<DossierDD> validator() {
		return validator;
	}
	
	@Override
	protected void initialisation() {
		super.initialisation();
		
		marie = (Boolean) parametres.get(constantResources.getFormParamMarie());
		nombreEnfant = (Integer) parametres.get(constantResources.getFormParamNombreEnfant());
		
		if(Code.NATURE_DEPLACEMENT_AFFECTATION.equals(entity.getDeplacement().getNature().getCode())){
			showDatePriseService = Boolean.TRUE;
			showServiceAcceuil = Boolean.TRUE;
		}else if(Code.NATURE_DEPLACEMENT_MUTATION.equals(entity.getDeplacement().getNature().getCode())){
			showDatePriseService = Boolean.TRUE;
			showDateCessationService = Boolean.TRUE;
			showServiceOrigine = Boolean.TRUE;
			showServiceAcceuil = Boolean.TRUE;
		}else if(Code.NATURE_DEPLACEMENT_RETRAITE.equals(entity.getDeplacement().getNature().getCode())){
			showDateMiseRetraite = Boolean.TRUE;
			showServiceOrigine = Boolean.TRUE;
		}
		/*
		entity.setGrade(genericService.findByClass(Grade.class, "A1"));
		entity.setDatePriseService(new Date());
		marie=false;
		entity.getDeplacement().setDateDepart(new Date());
		entity.getDeplacement().setDateArrivee(new Date());
		entity.getDeplacement().setLocaliteDepart(genericService.findByClass(Localite.class, "ABJ"));
		entity.getDeplacement().setLocaliteArrivee(genericService.findByClass(Localite.class, "BK"));
		entity.setService(genericService.findByClass(Section.class, Code.SECTION_SERV_EXP));*/
	}
	
	public void marieListener(){
		parametres.put(constantResources.getFormParamMarie(), marie);
		updatePieceJustificatives();
	}
	
	public void nombreEnfantListener(ValueChangeEvent valueChangeEvent){
		parametres.put(constantResources.getFormParamNombreEnfant(), valueChangeEvent.getNewValue());
		updatePieceJustificatives();
	}
	
	@Override
	protected void initCreateOperation() {
		super.initCreateOperation();
		entity.getDeplacement().setTypeDepense(genericService.findByClass(TypeDepense.class, String.class, Code.TYPE_DEPENSE_RMBOURSEMENT));
	}
		
}
