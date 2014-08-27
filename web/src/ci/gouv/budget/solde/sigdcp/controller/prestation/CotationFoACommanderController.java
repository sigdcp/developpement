package ci.gouv.budget.solde.sigdcp.controller.prestation;

import java.io.Serializable;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import ci.gouv.budget.solde.sigdcp.controller.ui.AbstractEntityListUIController;
import ci.gouv.budget.solde.sigdcp.model.Code;
import ci.gouv.budget.solde.sigdcp.model.prestation.DemandeCotationObseque;
import ci.gouv.budget.solde.sigdcp.service.prestation.DemandeCotationObsequeService;

@Named @ViewScoped
public class CotationFoACommanderController extends AbstractEntityListUIController<DemandeCotationObseque> implements Serializable {

	private static final long serialVersionUID = -2520806200426979462L;

	/*
	 * Services
	 */
	
	@Inject protected DemandeCotationObsequeService demandeCotationObsequeService;

	@Override
	protected void initialisation() {
		super.initialisation();
		title="Commande de frais d'obsèque";
		listTitle="Liste des demandes de cotation de frais d'obsèque";
		selectLabel = "bouton.selectionner";
		defaultSubmitCommand.setRendered(false);
	}

	@Override
	protected List<DemandeCotationObseque> load() {
		return (List<DemandeCotationObseque>) demandeCotationObsequeService.findATraiter(Code.NATURE_OPERATION_GENERATION_BCFO);
	}
	

	public String href(DemandeCotationObseque demandeCotationObseque){
		return navigationManager.url("commande_fo_form", new Object[]{webConstantResources.getRequestParamDemandeCotation(),demandeCotationObseque.getId(),webConstantResources.getRequestParamDossier(),demandeCotationObseque.getDossierId(),webConstantResources.getRequestParamTabMenuItemIndex(), userSessionManager.getUiMenuTabPrestationCommandeFoIndex()},false);
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
