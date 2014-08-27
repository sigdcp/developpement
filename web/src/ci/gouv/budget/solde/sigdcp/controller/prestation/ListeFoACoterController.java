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
import ci.gouv.budget.solde.sigdcp.model.prestation.DemandeCotationObseque;
import ci.gouv.budget.solde.sigdcp.service.prestation.DemandeCotationObsequeService;

@Named @ViewScoped
public class ListeFoACoterController extends AbstractEntityListUIController<DemandeCotationObseque> implements Serializable {

	private static final long serialVersionUID = -2520806200426979462L;
	
	/*
	 * Services
	 */
	
	@Inject protected DemandeCotationObsequeService demandeCotationObsequeService;
	/*
	 * Dto
	 */
	@Getter @Setter protected Boolean showMontant=Boolean.TRUE,showObservation=Boolean.FALSE,
			showValidation=Boolean.FALSE,showDateCreation=Boolean.TRUE;
	@Override
	protected void initialisation() {
		super.initialisation();
		title="Demande de cotation de frais d'obsèque";
		listTitle="Liste des demandes de frais d'obsèques";
		selectLabel = "bouton.selectionner";
		defaultSubmitCommand.setRendered(false);
	}

	@Override
	protected List<DemandeCotationObseque> load() {
		return (List<DemandeCotationObseque>) demandeCotationObsequeService.findATraiter(Code.NATURE_OPERATION_DEMANDE_COTATION);
	}
	
	@Override
	protected String identifierFieldName() {
		return "dossierId";
	}

	
	/*public String href(DemandeCotationObseque DemandeCotationObseque){
		return navigationManager.url("demande_cotation_fo_form", new Object[]{webConstantResources.getRequestParamDossier(),DemandeCotationObseque.getDossier().getId(),webConstantResources.getRequestParamTabMenuItemIndex(), userSessionManager.getUiMenuTabPrestationCotationFoIndex()},false);
	}*/
	
	@Override
	protected void hrefParameters(Map<String, Object> parameters, DemandeCotationObseque demandeCotationObseque) {		
		super.hrefParameters(parameters, demandeCotationObseque);
		parameters.put(webConstantResources.getRequestParamDossier(), demandeCotationObseque.getDossierId());
		parameters.put(webConstantResources.getRequestParamTabMenuItemIndex(), userSessionManager.getUiMenuTabPrestationCotationFoIndex());
	}
	
	@Override
	protected ProcessingType getProcessingType() {
		return ProcessingType.SINGLE;
	}
	
	

}
