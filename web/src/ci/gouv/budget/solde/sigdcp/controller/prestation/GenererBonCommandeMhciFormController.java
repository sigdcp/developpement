package ci.gouv.budget.solde.sigdcp.controller.prestation;

import java.io.Serializable;
import java.util.Arrays;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import lombok.Getter;
import lombok.Setter;

import org.omnifaces.util.Faces;

import ci.gouv.budget.solde.sigdcp.controller.ui.form.AbstractEntityFormUIController;
import ci.gouv.budget.solde.sigdcp.model.Code;
import ci.gouv.budget.solde.sigdcp.model.prestation.CommandeTitreTransport;
import ci.gouv.budget.solde.sigdcp.model.prestation.PrestataireDemandeCotationMissionId;
import ci.gouv.budget.solde.sigdcp.service.prestation.CommandeTitreTransportService;
import ci.gouv.budget.solde.sigdcp.service.prestation.PrestataireDemandeCotationMissionService;
import ci.gouv.budget.solde.sigdcp.service.resources.CRUDType;

@Named @ViewScoped
public class GenererBonCommandeMhciFormController extends AbstractEntityFormUIController<CommandeTitreTransport> implements Serializable {

	private static final long serialVersionUID = -2520806200426979462L;

	/*
	 * Services
	 */
	
	@Inject protected PrestataireDemandeCotationMissionService prestataireDemandeCotationMissionService;
	@Inject protected CommandeTitreTransportService commandeTitreTransportService;

	@Inject protected DemandeCotationMhciFormController demandeCotationMhciFormController;

	@Getter @Setter PrestataireDemandeCotationMissionId demandeId = new PrestataireDemandeCotationMissionId();
	
	
	@Getter @Setter private String titleDemande;
	
	@Override
	protected void initialisation() {
		crudType=CRUDType.CREATE;
		super.initialisation();
		try {
			demandeId.setDemandeCotationMissionId(Long.parseLong(Faces.getRequestParameter(webConstantResources.getRequestParamDemandeCotation())));
			demandeId.setPrestataireId(Long.parseLong(Faces.getRequestParameter(webConstantResources.getRequestParamPrestataire())));
			
			entity=commandeTitreTransportService.nouveau(prestataireDemandeCotationMissionService.findById(demandeId), Code.NATURE_OPERATION_GENERATION_BCTT);
			
		} catch (NumberFormatException e) {}
		
		demandeCotationMhciFormController.preRenderView();
		
		title="Commande de billet d'avion";
		titleDemande="Mission";	
		defaultSubmitCommand.onSuccessGoTo(navigationManager.url("cotation_mhci_a_commander",new Object[]{webConstantResources.getRequestParamTabMenuItemIndex(),1},false), "", null);
		
	}

	
	@Override
	protected void onDefaultSubmitAction() throws Exception {		
		commandeTitreTransportService.valider(Arrays.asList(entity));
		
	}
	
}
