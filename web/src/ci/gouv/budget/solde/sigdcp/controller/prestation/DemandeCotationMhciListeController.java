package ci.gouv.budget.solde.sigdcp.controller.prestation;

import java.io.Serializable;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import ci.gouv.budget.solde.sigdcp.controller.ui.AbstractEntityListUIController;
import ci.gouv.budget.solde.sigdcp.model.Code;
import ci.gouv.budget.solde.sigdcp.model.prestation.PrestataireDemandeCotationMission;
import ci.gouv.budget.solde.sigdcp.service.prestation.PrestataireDemandeCotationMissionService;

@Named @ViewScoped
public class DemandeCotationMhciListeController extends AbstractEntityListUIController<PrestataireDemandeCotationMission> implements Serializable {

	private static final long serialVersionUID = -2520806200426979462L;

	/*
	 * Services
	 */
	
	@Inject protected PrestataireDemandeCotationMissionService prestationDemandeCotationMissionService;

	@Override
	protected void initialisation() {
		super.initialisation();
		title="Repondre à une demande de cotation";
		listTitle="Liste des demandes de cotation MHCI";
		selectLabel = "bouton.selectionner";
		defaultSubmitCommand.setRendered(false);
	}

	@Override
	protected List<PrestataireDemandeCotationMission> load() {
		return (List<PrestataireDemandeCotationMission>) prestationDemandeCotationMissionService.findATraiter(Code.NATURE_OPERATION_REPONDRE_COTATION);
		//return prestationDemandeCotationMissionService.findAll();
	}
	

	public String href(PrestataireDemandeCotationMission prestataireDemandeCotationMission){
		return navigationManager.url("coter_demande_mhci_form", new Object[]{webConstantResources.getRequestParamDemandeCotation(),prestataireDemandeCotationMission.getId().getDemandeCotationMissionId()},false);
	}
	
	
	@Override
	protected ProcessingType getProcessingType() {
		return ProcessingType.SINGLE;
	}
	@Override
	protected String identifierFieldName() {
		return "id";
	}
	
}
