package ci.gouv.budget.solde.sigdcp.controller.prestation;

import java.io.Serializable;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import lombok.Getter;
import lombok.Setter;

import org.omnifaces.util.Faces;

import ci.gouv.budget.solde.sigdcp.controller.fichier.PieceJustificativeUploader;
import ci.gouv.budget.solde.sigdcp.controller.ui.AbstractEntityListUIController;
import ci.gouv.budget.solde.sigdcp.model.Code;
import ci.gouv.budget.solde.sigdcp.model.dossier.DossierObseques;
import ci.gouv.budget.solde.sigdcp.model.prestation.CommandeObseque;
import ci.gouv.budget.solde.sigdcp.service.dossier.DossierObsequesService;
import ci.gouv.budget.solde.sigdcp.service.prestation.CommandeObsequeService;
import ci.gouv.budget.solde.sigdcp.service.prestation.DemandeCotationObsequeService;

@Named @ViewScoped
public class CommandeFoFormController extends AbstractEntityListUIController<CommandeObseque> implements Serializable {

	private static final long serialVersionUID = -2520806200426979462L;

	/*
	 * Services
	 */
	
	@Inject protected CommandeObsequeService commandeObsequeService;
	@Inject protected DossierObsequesService dossierObsequesService;
	@Inject protected DemandeCotationObsequeService demandeCotationObsequeService;
	
	@Inject protected DemandeCotationFoFormController demandeCotationFoFormController;
	
	
	@Getter @Setter private Long numero;
	@Getter @Setter private String titleDemande;
	@Getter @Setter private PieceJustificativeUploader uploader=new PieceJustificativeUploader();
	@Getter @Setter private CommandeObseque voir;
	@Getter @Setter DossierObseques dossier=new DossierObseques();
	

	@Override
	protected void initialisation() {
		super.initialisation();
		try {
			numero = Long.parseLong(Faces.getRequestParameter(webConstantResources.getRequestParamDemandeCotation()));
			dossier=dossierObsequesService.findById(demandeCotationObsequeService.findById(numero).getDossierId());
		} catch (NumberFormatException e) {}
		
		title="Commande de Frais d'obsèque";
		titleDemande="Demande de cotation";
		listTitle="Propositions de cotation des prestataires";
		selectLabel = "bouton.selectionner";
		defaultSubmitCommand.setRendered(false);
		
		demandeCotationFoFormController.preRenderView();
		
		//showCheckBox = false;
		//showRadio = true;
		//showActionsColumn=false;
	}

	@Override
	protected List<CommandeObseque> load() {
		return (List<CommandeObseque>) commandeObsequeService.findATraiter(Code.NATURE_OPERATION_GENERATION_BCFO);
	}
	

	public String href(CommandeObseque commande){
		return navigationManager.url("generer_commande_fo_form", new Object[]{webConstantResources.getRequestParamDemandeCotation(),commande.getPrestataireDemandeCotationObseque().getId().getDemandeCotationObsequeId(),webConstantResources.getRequestParamPrestataire(),commande.getPrestataireDemandeCotationObseque().getId().getPrestataireId(),webConstantResources.getRequestParamDossier(),dossier.getId(),webConstantResources.getRequestParamTabMenuItemIndex(), userSessionManager.getUiMenuTabPrestationCommandeFoIndex()},false);
	}
	 
	@Override
	protected String identifierFieldName() {
		return "commandeId";
	}
	@Override
	protected ProcessingType getProcessingType() {
		return ProcessingType.SINGLE;
	}
	

	
}
