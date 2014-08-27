package ci.gouv.budget.solde.sigdcp.controller.prestation;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import lombok.Getter;
import lombok.Setter;
import ci.gouv.budget.solde.sigdcp.controller.ui.AbstractEntityListUIController;
import ci.gouv.budget.solde.sigdcp.model.Code;
import ci.gouv.budget.solde.sigdcp.model.prestation.DemandeCotationMission;
import ci.gouv.budget.solde.sigdcp.service.prestation.DemandeCotationMissionService;

@Named @ViewScoped
public class ListeMissionACoterController extends AbstractEntityListUIController<DemandeCotationMission> implements Serializable {

	private static final long serialVersionUID = -2520806200426979462L;
	
	/*
	 * Services
	 */
	
	@Inject protected DemandeCotationMissionService demandeCotationMissionService;
	/*
	 * Dto
	 */
	@Getter @Setter protected Boolean showMontant=Boolean.TRUE,showObservation=Boolean.FALSE,
			showValidation=Boolean.FALSE,showDateCreation=Boolean.TRUE;
	@Override
	protected void initialisation() {
		super.initialisation();
		title="Demande de cotation de billet d'avion";
		listTitle="Liste des missions en cours";
		selectLabel = "bouton.selectionner";
		defaultSubmitCommand.setRendered(false);
	}

	@Override
	protected List<DemandeCotationMission> load() {
		return (List<DemandeCotationMission>) demandeCotationMissionService.findATraiter(Code.NATURE_OPERATION_DEMANDE_COTATION);
	}
	
	@Override
	protected String identifierFieldName() {
		return "missionId";
	}

	@Override
	protected void hrefParameters(Map<String, Object> parameters, DemandeCotationMission demandeCotationMission) {		
		super.hrefParameters(parameters, demandeCotationMission);
		parameters.put(webConstantResources.getRequestParamMission(), demandeCotationMission.getMission().getId());
		parameters.put(webConstantResources.getRequestParamTabMenuItemIndex(), userSessionManager.getUiMenuTabPrestationCotationMhciIndex());
	}
	
	@Override
	protected ProcessingType getProcessingType() {
		return ProcessingType.SINGLE;
	}
	
	

}
