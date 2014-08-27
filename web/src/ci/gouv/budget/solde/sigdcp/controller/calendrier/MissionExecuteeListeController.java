package ci.gouv.budget.solde.sigdcp.controller.calendrier;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.omnifaces.util.Faces;

import ci.gouv.budget.solde.sigdcp.model.Code;
import ci.gouv.budget.solde.sigdcp.model.calendrier.MissionExecutee;

@Named @ViewScoped
public class MissionExecuteeListeController extends AbstractMissionExecuteeListeController implements Serializable {

	private static final long serialVersionUID = -2520806200426979462L;

	@Override
	protected void initialisation() {
		super.initialisation();
		title="Liste des missions organisées";
		selectLabel = "bouton.consulter";
		//editDetailsCommand.setRendered(false);
	}
	
	@Override
	protected Collection<MissionExecutee> missions() {
		return missionExecuteeService.findMissionOrganisees();
	}
	

	public String href(MissionExecutee missionExecutee){
		return navigationManager.url("demande_mission_pointfocal", new Object[]{webConstantResources.getRequestParamMission(),missionExecutee.getId()},false);
	}

	@Override
	protected void onRemoveDetailsCommandAction(MissionExecutee dto) throws Exception {
		Faces.redirect(Faces.getServletContext().getContextPath()+"/"+_href(dto,webConstantResources.getRequestParamCrudDelete()));
	}
	
	@Override
	public Boolean canRemove(MissionExecutee dto) {
		return true;
	}
	
	@Override
	public Boolean actionable(MissionExecutee dto){
		return Code.NATURE_OPERATION_SAISIE.equals(dto.getNatureOperationCode()) || Code.NATURE_OPERATION_SOUMISSION.equals(dto.getNatureOperationCode()) 
			|| Code.NATURE_OPERATION_DEPOT.equals(dto.getNatureOperationCode());
	}
	
	@Override
	public String actionLabel(MissionExecutee dto){
		
		switch(dto.getNatureOperationCode()){
		case Code.NATURE_OPERATION_SAISIE:case Code.NATURE_OPERATION_SOUMISSION:return "Modifier";
		case Code.NATURE_OPERATION_TRANSMISSION_SAISIE_A_ORGANISATEUR: return "Completer";
		case Code.NATURE_OPERATION_DEPOT: return "Déposer";
		}
		return null;
	}
	
	@Override
	public String actionHref(MissionExecutee dto){
		return _href(dto, webConstantResources.getRequestParamCrudUpdate());
	}
	
	@Override
	protected void hrefParameters(Map<String, Object> parameters, MissionExecutee missionExecutee) {
		super.hrefParameters(parameters, missionExecutee);
		parameters.put(webConstantResources.getRequestParamMission(),missionExecutee.getId());
	}
	
	@Override
	protected Object hrefObjectOutcome(MissionExecutee missionExecutee) {
		return missionExecutee;
	}
}
