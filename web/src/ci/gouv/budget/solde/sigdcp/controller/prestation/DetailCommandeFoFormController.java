package ci.gouv.budget.solde.sigdcp.controller.prestation;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import lombok.Getter;
import lombok.Setter;

import org.omnifaces.util.Faces;

import ci.gouv.budget.solde.sigdcp.controller.ui.form.AbstractEntityFormUIController;
import ci.gouv.budget.solde.sigdcp.model.Code;
import ci.gouv.budget.solde.sigdcp.model.prestation.CommandeObseque;
import ci.gouv.budget.solde.sigdcp.model.prestation.PrestataireDemandeCotationObsequeId;
import ci.gouv.budget.solde.sigdcp.service.prestation.CommandeObsequeService;
import ci.gouv.budget.solde.sigdcp.service.prestation.PrestataireDemandeCotationObsequeService;

@Named @ViewScoped
public class DetailCommandeFoFormController extends AbstractEntityFormUIController<CommandeObseque> implements Serializable {

	private static final long serialVersionUID = -2520806200426979462L;

	/*
	 * Services
	 */
	
	@Inject protected PrestataireDemandeCotationObsequeService prestataireDemandeCotationObsequeService;
	@Inject protected CommandeObsequeService commandeObsequeService;

	@Inject protected DemandeCotationFoFormController demandeCotationFoFormController;

	@Getter @Setter PrestataireDemandeCotationObsequeId demandeId = new PrestataireDemandeCotationObsequeId();
	
	
	@Getter @Setter private String titleDemande;
	
	@Override
	protected void initialisation() {
		//crudType=CRUDType.CREATE;
		super.initialisation();
		try {
			entity=commandeObsequeService.find(Long.parseLong(Faces.getRequestParameter(webConstantResources.getRequestParamCommande())),null);
		} catch (NumberFormatException e) {}
		
		demandeCotationFoFormController.preRenderView();
		
		title="Commande de frais d'obsèque";
		titleDemande="Indemnité Obsèque";
		
		defaultSubmitCommand.setRendered(false);
		
	}

	
	
}
