package ci.gouv.budget.solde.sigdcp.controller.demande;

import java.io.Serializable;
import java.util.Arrays;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import lombok.Getter;
import lombok.Setter;

import org.primefaces.model.UploadedFile;

import ci.gouv.budget.solde.sigdcp.controller.NavigationManager;
import ci.gouv.budget.solde.sigdcp.model.Code;
import ci.gouv.budget.solde.sigdcp.model.calendrier.MissionExecutee;
import ci.gouv.budget.solde.sigdcp.model.dossier.DossierMission;
import ci.gouv.budget.solde.sigdcp.model.dossier.NatureDeplacement;
import ci.gouv.budget.solde.sigdcp.service.calendrier.MissionExecuteeService;
import ci.gouv.budget.solde.sigdcp.service.dossier.DossierMissionService;
import ci.gouv.budget.solde.sigdcp.service.resources.CRUDType;

@Getter @Setter @Named @ViewScoped
public class FaireDemandeMissionController extends AbstractFaireDemandeController<DossierMission, DossierMissionService> implements Serializable {

	private static final long serialVersionUID = -8840662624432472475L;

	@Inject private MissionExecuteeService missionExecuteeService;
	@Inject private DossierMissionService dossierMissionService;
	
	private MissionExecutee missionExecutee;
	
	private UploadedFile file;
	private Boolean showRetournerFd,editerRetourFd;
	
	@Override
	protected void afterDtoInit() {
		natureDeplacement = genericService.findByClass(NatureDeplacement.class, Code.NATURE_DEPLACEMENT_MISSION_HCI);
		super.afterDtoInit();
		if(entity!=null)
			missionExecutee = missionExecuteeService.findByDossier(entity);
		
		 showRetournerFd=entity.getFeuilleDeplacement()!=null || Code.NATURE_OPERATION_RETOUR_FD.equals(entity.getTraitable().getNatureOperation().getCode());
		 editerRetourFd=Code.NATURE_OPERATION_RETOUR_FD.equals(entity.getTraitable().getNatureOperation().getCode());
		
	}
	
	@Override
	protected void afterInitialisation() {
		super.afterInitialisation();
		if(enregistrerCommand!=null)
			enregistrerCommand.setValue(defaultSubmitCommand.getValue());
	}
		
	@Override
	protected DossierMissionService getDossierService() {
		return dossierMissionService;
	}
	
	@Override
	protected CRUDType operationSaisie() {
		if(Code.NATURE_OPERATION_TRANSMISSION_SAISIE_A_BENEFICIAIRE.equals(entity.getTraitable().getDernierTraitement().getOperation().getNature().getCode()))
			return CRUDType.UPDATE;
		return CRUDType.READ;
	}
	
	@Override
	protected String action() {
		if(entity.getTraitable().getDernierTraitement()==null || Code.NATURE_OPERATION_SAISIE.equals(entity.getTraitable().getDernierTraitement().getOperation().getNature().getCode()))
			return webConstantResources.getRequestParamCrudRead();
		return super.action();
	}
	
	@Override
	protected String onEnregistrerSuccessOutcome() {
		return navigationManager.url(NavigationManager.OUTCOME_SUCCESS_VIEW,new Object[]{webConstantResources.getRequestParamMessageId(),"notification.demande.enregistree",
				webConstantResources.getRequestParamUrl(),navigationManager.url("demandeliste",null,false,false)},true);
	}
	
	@Override
	protected void onDefaultSubmitAction() throws Exception {
		super.onDefaultSubmitAction();
		switch(entity.getTraitable().getNatureOperation().getCode()){
		case Code.NATURE_OPERATION_RETOUR_FD:
			pieceJustificativeUploader.process();
			dossierMissionService.retournerFeuilleDeplacment(Arrays.asList(entity.getFeuilleDeplacement()));
			break;
		}		
	}
	
}
