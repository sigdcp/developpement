package ci.gouv.budget.solde.sigdcp.controller.prestation;

import java.io.Serializable;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import ci.gouv.budget.solde.sigdcp.controller.ui.AbstractEntityListUIController;
import ci.gouv.budget.solde.sigdcp.model.Code;
import ci.gouv.budget.solde.sigdcp.model.prestation.DemandeCotationMission;
import ci.gouv.budget.solde.sigdcp.service.prestation.DemandeCotationMissionService;

@Named @ViewScoped
public class CotationMhciACommanderController extends AbstractEntityListUIController<DemandeCotationMission> implements Serializable {

	private static final long serialVersionUID = -2520806200426979462L;

	/*
	 * Services
	 */
	
	@Inject protected DemandeCotationMissionService demandeCotationMissionService;

	@Override
	protected void initialisation() {
		super.initialisation();
		title="Commande de billet d'avion";
		listTitle="Liste des demandes de cotation MHCI";
		selectLabel = "bouton.selectionner";
		defaultSubmitCommand.setRendered(false);
	}

	@Override
	protected List<DemandeCotationMission> load() {
		return (List<DemandeCotationMission>) demandeCotationMissionService.findATraiter(Code.NATURE_OPERATION_GENERATION_BCTT);
	}
	

	public String href(DemandeCotationMission demandeCotationMission){
		return navigationManager.url("commande_mhci_form", new Object[]{webConstantResources.getRequestParamDemandeCotation(),demandeCotationMission.getId(),webConstantResources.getRequestParamMission(),demandeCotationMission.getMissionId(),webConstantResources.getRequestParamTabMenuItemIndex(), userSessionManager.getUiMenuTabPrestationCommandeMhciIndex()},false);
	}

	
	@Override
	protected String identifierFieldName() {
		return "id";
	}
	@Override
	protected ProcessingType getProcessingType() {
		return ProcessingType.SINGLE;
	}
	
}
