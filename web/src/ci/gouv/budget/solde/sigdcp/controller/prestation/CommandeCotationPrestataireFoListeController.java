package ci.gouv.budget.solde.sigdcp.controller.prestation;

import java.io.Serializable;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import ci.gouv.budget.solde.sigdcp.controller.ui.AbstractEntityListUIController;
import ci.gouv.budget.solde.sigdcp.model.prestation.CommandeObseque;
import ci.gouv.budget.solde.sigdcp.service.prestation.CommandeObsequeService;

@Named @ViewScoped
public class CommandeCotationPrestataireFoListeController extends AbstractEntityListUIController<CommandeObseque> implements Serializable {

	private static final long serialVersionUID = -2520806200426979462L;

	/*
	 * Services
	 */
	
	@Inject protected CommandeObsequeService commandeObsequeService;

	@Override
	protected void initialisation() {
		super.initialisation();
		title="Commande de frais d'obsèques";
		listTitle="Liste des commandes de frais d'obsèques";
		selectLabel = "bouton.selectionner";
		defaultSubmitCommand.setRendered(false);
	}

	@Override
	protected List<CommandeObseque> load() {
		return (List<CommandeObseque>) commandeObsequeService.findByPrestataire(userSessionManager.getCompteUtilisateur().getUtilisateur().getId());
		
	}
	

	public String href(CommandeObseque commandeObseque){
		return navigationManager.url("detail_commande_fo_form", new Object[]{webConstantResources.getRequestParamCommande(),commandeObseque.getId(),
				webConstantResources.getRequestParamDossier(),commandeObseque.getPrestataireDemandeCotationObseque().getDossier().getId()},false);
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
