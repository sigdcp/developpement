package ci.gouv.budget.solde.sigdcp.controller.calendrier;

import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import lombok.Getter;
import lombok.Setter;

import org.omnifaces.util.Faces;

import ci.gouv.budget.solde.sigdcp.controller.ui.AbstractEntityListUIController;
import ci.gouv.budget.solde.sigdcp.model.calendrier.MissionExecutee;
import ci.gouv.budget.solde.sigdcp.model.dossier.NatureDeplacement;
import ci.gouv.budget.solde.sigdcp.model.traitement.Statut;
import ci.gouv.budget.solde.sigdcp.service.calendrier.MissionExecuteeService;

public abstract class AbstractMissionExecuteeListeController extends AbstractEntityListUIController<MissionExecutee> implements Serializable {

	private static final long serialVersionUID = -2412073347414420827L;

	/*
	 * Services
	 */
	
	@Inject protected MissionExecuteeService missionExecuteeService;
	/*
	 * Dto
	 */
	@Getter @Setter protected Boolean showMontant=Boolean.TRUE,showObservation=Boolean.FALSE,
			showValidation=Boolean.FALSE,showDateCreation=Boolean.TRUE;

	/*
	 * Paramètres url
	 */
	@Getter @Setter protected Statut statut;
	@Getter @Setter protected NatureDeplacement natureDeplacement;
	
	@Override
	protected void initialisation() {
		//statut = dynamicEnumerationService.findByClass(Statut.class, codeStatut());
		//natureDeplacement = dynamicEnumerationService.findByClass(NatureDeplacement.class, Code.NATURE_DEPLACEMENT_AFFECTATION);
		super.initialisation();	
		//defaultSubmitCommand.setValue(text("bouton.valider"));
		defaultSubmitCommand.setRendered(false);
		//enableSearch();
	}
	
	@Override
	protected String identifierFieldName() {
		return "id";
	}
	
	@Override
	protected List<MissionExecutee> load() {
		return new LinkedList<>(missions());
	}
	
	protected abstract Collection<MissionExecutee> missions();
	
	@Override
	protected void onRemoveDetailsCommandAction(MissionExecutee dto) throws Exception {
		Faces.redirect(Faces.getServletContext().getContextPath()+"/"+_href(dto,webConstantResources.getRequestParamCrudDelete()));
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
	
	@Override
	protected String detailsOutcome(MissionExecutee missionExecutee) {
		return navigationManager.outcome(missionExecutee);
	}
	
	@Override
	protected void detailsOutcomeParameters(Map<String, List<String>> parameters, MissionExecutee missionExecutee) {
		addParameters(parameters, webConstantResources.getRequestParamMission(), missionExecutee.getId()+"");
		addParameters(parameters, webConstantResources.getRequestParamCrudType(), webConstantResources.getRequestParamCrudRead());
	}
	
	@Override
	protected ProcessingType getProcessingType() {
		return ProcessingType.SINGLE;
	}
}
