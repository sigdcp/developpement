package ci.gouv.budget.solde.sigdcp.controller.dossier;

import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedList;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import lombok.Getter;
import lombok.Setter;
import ci.gouv.budget.solde.sigdcp.controller.application.UserSessionManager;
import ci.gouv.budget.solde.sigdcp.controller.ui.form.AbstractEntityFormUIController;
import ci.gouv.budget.solde.sigdcp.controller.ui.form.command.FormCommand;
import ci.gouv.budget.solde.sigdcp.model.calendrier.Mission;
import ci.gouv.budget.solde.sigdcp.model.dossier.DossierMission;
import ci.gouv.budget.solde.sigdcp.model.dossier.PieceJustificative;
import ci.gouv.budget.solde.sigdcp.service.calendrier.MissionService;
import ci.gouv.budget.solde.sigdcp.service.calendrier.MissionServiceImpl;
import ci.gouv.budget.solde.sigdcp.service.dossier.DossierMissionService;

@Named @ViewScoped @Getter @Setter
public class EnregistrerDemandeMHCIFormController extends AbstractEntityFormUIController<Mission> implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	/*
	 * Services
	 */
	@Inject private MissionService missionService;
	@Inject private UserSessionManager userSessionManager;
	
	@Getter private Collection<DossierMissionDto> dossierMissionDtos = new LinkedList<>();
	
	/*
	 * Actions
	 */
	//protected FormCommand<Do> enregistrerCommand;
	
	@Override
	protected void initialisation() {
		super.initialisation();
		entity = missionService.findSaisieByPersonne(userSessionManager.getUser());
		if(entity==null)
			entity=new Mission();
		requiredEnabled=Boolean.FALSE;
	}

	@Override
	protected void onDefaultSubmitAction() throws Exception {
		Collection<Collection<PieceJustificative>> pieceJustificatives = new LinkedList<>();
		Collection<DossierMission> dossiers = new LinkedList<>();
		for(DossierMissionDto dto : dossierMissionDtos){
			dossiers.add(dto.getDossierMission());
			pieceJustificatives.add(dto.getPieceJustificativeUploader().process());
		}
		missionService.enregistrer(entity, dossiers,pieceJustificatives);
		
	}

	public void ajouterParticipant(){
		dossierMissionDtos.add(new DossierMissionDto(new DossierMission()));
	}
}
	