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
 
@Named @ViewScoped
public class EnregistrerDemandeDDController extends AbstractDossierUIController<DossierDD,DossierDDService> implements Serializable {
	
	private static final long serialVersionUID = -611561465509440427L;
	
	/*
	 * Services
	 */  
	@Inject private DossierDDService dossierDDService;
	
	
	@Getter @Setter private Boolean marie;
	@Getter @Setter private Integer nombreEnfant;
	

	/*
	 * Attributs de parametrages de la vue
	 */
	@Getter Boolean showDatePriseService = Boolean.FALSE;
	@Getter Boolean showDateCessationService = Boolean.FALSE;
	@Getter Boolean showDateMiseRetraite = Boolean.FALSE;
	@Getter Boolean showServiceOrigine = Boolean.FALSE;
	@Getter Boolean showServiceAcceuil = Boolean.FALSE;
	
	@Setter @Getter PieceJustificative extraitNaissanceUploader;
	
	@Override
	protected DossierDDService getDossierService() {
		return dossierDDService;
	}

	@Override
	public void __firstPreRenderView__() {
		super.__firstPreRenderView__();
		//DossierDDValidator validator = new DossierDDValidator();
		//enregistrerAction.setValidator(validator);
		//soumettreAction.setValidator(validator);
		
		
		if(isNatureDeplacementAffectation()){
			showDatePriseService = Boolean.TRUE;
			showServiceAcceuil = Boolean.TRUE;
		}else if(isNatureDeplacementMutation()){
			showDatePriseService = Boolean.TRUE;
			showDateCessationService = Boolean.TRUE;
			showServiceOrigine = Boolean.TRUE;
			showServiceAcceuil = Boolean.TRUE;
		}else if(isNatureDeplacementDepartRetraite()){
			showDateMiseRetraite = Boolean.TRUE;
			showServiceOrigine = Boolean.TRUE;
		}
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
