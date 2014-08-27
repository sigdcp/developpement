package ci.gouv.budget.solde.sigdcp.controller.prestation;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import lombok.Getter;
import lombok.Setter;

import org.omnifaces.util.Faces;

import ci.gouv.budget.solde.sigdcp.controller.ui.form.AbstractEntityFormUIController;
import ci.gouv.budget.solde.sigdcp.model.prestation.CommandeTitreTransport;
import ci.gouv.budget.solde.sigdcp.model.prestation.PrestataireDemandeCotationMissionId;
import ci.gouv.budget.solde.sigdcp.service.prestation.CommandeTitreTransportService;
import ci.gouv.budget.solde.sigdcp.service.prestation.PrestataireDemandeCotationMissionService;

@Named @ViewScoped
public class DetailCommandeMhciFormController extends AbstractEntityFormUIController<CommandeTitreTransport> implements Serializable {

	private static final long serialVersionUID = -2520806200426979462L;

	/*
	 * Services
	 */
	
	@Inject protected PrestataireDemandeCotationMissionService prestataireDemandeCotationMissionService;
	@Inject protected CommandeTitreTransportService commandeTitreTransportService;

	@Inject protected DemandeCotationMhciFormController demandeCotationMhciFormController;

	@Getter @Setter PrestataireDemandeCotationMissionId demandeId = new PrestataireDemandeCotationMissionId();
	

	
	@Override
	protected void initialisation() {
		super.initialisation();
		try {
			entity=commandeTitreTransportService.find(Long.parseLong(Faces.getRequestParameter(webConstantResources.getRequestParamCommande())),null);
			
		} catch (NumberFormatException e) {}
		
		demandeCotationMhciFormController.preRenderView();
		
		title="Détail de la Commande de billet d'avion N°"+Faces.getRequestParameter(webConstantResources.getRequestParamCommande());
		defaultSubmitCommand.setRendered(false);
	}

	
	
}
