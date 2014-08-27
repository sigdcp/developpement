package ci.gouv.budget.solde.sigdcp.controller.prestation;

import java.io.Serializable;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import ci.gouv.budget.solde.sigdcp.controller.ui.AbstractEntityListUIController;
import ci.gouv.budget.solde.sigdcp.model.prestation.CommandeTitreTransport;
import ci.gouv.budget.solde.sigdcp.service.prestation.CommandeTitreTransportService;

@Named @ViewScoped
public class CommandeCotationPrestataireMhciListeController extends AbstractEntityListUIController<CommandeTitreTransport> implements Serializable {

	private static final long serialVersionUID = -2520806200426979462L;

	/*
	 * Services
	 */
	
	@Inject protected CommandeTitreTransportService commandeTitreTransportService;

	@Override
	protected void initialisation() {
		super.initialisation();
		title="Commande de billet d'avion";
		listTitle="Liste des commandes de billets d'avions";
		selectLabel = "bouton.selectionner";
		defaultSubmitCommand.setRendered(false);
	}

	@Override
	protected List<CommandeTitreTransport> load() {
		return (List<CommandeTitreTransport>) commandeTitreTransportService.findByPrestataire(userSessionManager.getCompteUtilisateur().getUtilisateur().getId());
	}
	
	@Override
	public String href(CommandeTitreTransport commandeTitreTransport){
		return navigationManager.url("detail_commande_mhci_form", new Object[]{webConstantResources.getRequestParamCommande(),commandeTitreTransport.getId(),
				webConstantResources.getRequestParamMission(), commandeTitreTransport.getPrestataireDemandeCotationMission().getMissionExecutee().getId()},false);
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
