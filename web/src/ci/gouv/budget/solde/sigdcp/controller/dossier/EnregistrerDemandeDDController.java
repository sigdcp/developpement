package ci.gouv.budget.solde.sigdcp.controller.dossier;


import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import lombok.Getter;
import lombok.Setter;
import ci.gouv.budget.solde.sigdcp.model.Code;
import ci.gouv.budget.solde.sigdcp.model.dossier.DossierDD;
import ci.gouv.budget.solde.sigdcp.model.dossier.PieceJustificative;
import ci.gouv.budget.solde.sigdcp.service.dossier.DossierDDService;
import ci.gouv.budget.solde.sigdcp.service.utils.validaton.DossierDDValidator;
 
@Named @ViewScoped
public class EnregistrerDemandeDDController extends AbstractDossierUIControllerController<DossierDD> implements Serializable {
	
	private static final long serialVersionUID = -611561465509440427L;
	
	/*
	 * Services
	 */  
	@Inject protected DossierDDService dossierDDService;
	
	
	@Getter @Setter private Boolean marie;
	@Getter @Setter private Integer nombreEnfant;
	

	/*
	 * Attributs de parametrages de la vue
	 */
	@Getter Boolean showDatePriseService;
	@Getter Boolean showDateCessationService;
	@Getter Boolean showDateMiseRetraite;
	@Getter Boolean showserviceOrigine;
	@Getter Boolean showserviceAcceuil;
	
	@Setter @Getter PieceJustificative extraitNaissanceUploader;
	
	@Override @Inject
	protected DossierDDService getDossierService() {
		return dossierDDService;
	}
/*
	@Override
	protected Boolean valide() {
		return Boolean.TRUE;
	}*/

	@Override
	public void __firstPreRenderView__() {
		super.__firstPreRenderView__();
		DossierDDValidator validator = new DossierDDValidator();
		//enregistrerPart1Action.setValidator(validator);
		enregistrerPart1Action.setImmediate(Boolean.TRUE);
		enregistrerPart2Action.setValidator(validator);
		enregistrerPart2Action.setImmediate(Boolean.TRUE);
		soumettreAction.setValidator(validator);
		
		if(isNatureDeplacementAffectation()){
			showDatePriseService = Boolean.TRUE;
			showserviceAcceuil = Boolean.TRUE;
		}else if(isNatureDeplacementMutation()){
			showDatePriseService = Boolean.TRUE;
			showDateCessationService = Boolean.TRUE;
			showserviceOrigine = Boolean.TRUE;
			showserviceAcceuil = Boolean.TRUE;
		}else if(isNatureDeplacementDepartRetraite()){
			showDateMiseRetraite = Boolean.TRUE;
			showserviceOrigine = Boolean.TRUE;
		}
		
		// test - debugging purpose - to set to TRUE or removed to use default value
		//fieldValueRequiredEnabled = Boolean.FALSE;
	}
		
	public boolean isNatureDeplacementAffectation(){
		return Code.NATURE_DEPLACEMENT_AFFECTATION.equals(entity.getDeplacement().getNature().getCode());
	}
	
	public boolean isNatureDeplacementDepartRetraite(){
		return Code.NATURE_DEPLACEMENT_RETRAITE.equals(entity.getDeplacement().getNature().getCode());
	}
	
	public boolean isNatureDeplacementMutation(){
		return Code.NATURE_DEPLACEMENT_MUTATION.equals(entity.getDeplacement().getNature().getCode());
	}

}
