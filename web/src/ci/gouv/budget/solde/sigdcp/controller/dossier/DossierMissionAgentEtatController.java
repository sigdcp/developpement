package ci.gouv.budget.solde.sigdcp.controller.dossier;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import lombok.Getter;
import lombok.Setter;
import ci.gouv.budget.solde.sigdcp.model.calendrier.MissionExecutee;
import ci.gouv.budget.solde.sigdcp.model.dossier.DossierMission;
import ci.gouv.budget.solde.sigdcp.service.calendrier.MissionExecuteeService;
import ci.gouv.budget.solde.sigdcp.service.dossier.DossierMissionService;

@Getter @Setter @Named @ViewScoped
public class DossierMissionAgentEtatController extends AbstractDossierUIController<DossierMission, DossierMissionService> implements Serializable {

	private static final long serialVersionUID = -8840662624432472475L;

	@Inject private MissionExecuteeService missionExecuteeService;
	@Inject private DossierMissionService dossierMissionService;
	
	private MissionExecutee missionExecutee;

	@Override
	protected void initialisation() {
		super.initialisation();
		missionExecutee = missionExecuteeService.findByDossier(entity);
	}
	
	@Override
	protected void afterInitialisation() {
		super.afterInitialisation();
		if(Boolean.TRUE.equals(showCourrier))
			defaultSubmitCommand.setRendered(Boolean.FALSE);
	}
	
	@Override
	protected DossierMissionService getDossierService() {
		return dossierMissionService;
	}
	
	@Override
	public boolean isEditable() {
		return super.isEditable() && entity.getDernierTraitement().getMotif()!=null;
	}
	
}
