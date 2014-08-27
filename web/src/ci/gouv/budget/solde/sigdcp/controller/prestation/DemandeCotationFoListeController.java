package ci.gouv.budget.solde.sigdcp.controller.prestation;

import java.io.Serializable;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import ci.gouv.budget.solde.sigdcp.controller.ui.AbstractEntityListUIController;
import ci.gouv.budget.solde.sigdcp.model.Code;
import ci.gouv.budget.solde.sigdcp.model.prestation.PrestataireDemandeCotationObseque;
import ci.gouv.budget.solde.sigdcp.service.prestation.PrestataireDemandeCotationObsequeService;

@Named @ViewScoped
public class DemandeCotationFoListeController extends AbstractEntityListUIController<PrestataireDemandeCotationObseque> implements Serializable {

	private static final long serialVersionUID = -2520806200426979462L;

	/*
	 * Services
	 */
	
	@Inject protected PrestataireDemandeCotationObsequeService prestationDemandeCotationObsequeService;

	@Override
	protected void initialisation() {
		super.initialisation();
		title="Repondre à une demande de cotation";
		listTitle="Liste des demandes de cotation de frais d'obsèques";
		selectLabel = "bouton.selectionner";
		defaultSubmitCommand.setRendered(false);
	}

	@Override
	protected List<PrestataireDemandeCotationObseque> load() {
		return (List<PrestataireDemandeCotationObseque>) prestationDemandeCotationObsequeService.findATraiter(Code.NATURE_OPERATION_REPONDRE_COTATION);
		//return prestationDemandeCotationObsequeService.findAll();
	}
	

	public String href(PrestataireDemandeCotationObseque prestataireDemandeCotationObseque){
		return navigationManager.url("coter_demande_fo_form", new Object[]{webConstantResources.getRequestParamDemandeCotation(),prestataireDemandeCotationObseque.getId().getDemandeCotationObsequeId()},false);
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
