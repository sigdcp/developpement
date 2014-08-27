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
import ci.gouv.budget.solde.sigdcp.model.calendrier.MissionExecutee;
import ci.gouv.budget.solde.sigdcp.model.prestation.CommandeTitreTransport;
import ci.gouv.budget.solde.sigdcp.service.calendrier.MissionExecuteeService;
import ci.gouv.budget.solde.sigdcp.service.prestation.CommandeTitreTransportService;
import ci.gouv.budget.solde.sigdcp.service.prestation.DemandeCotationMissionService;

@Named @ViewScoped
public class CommandeMhciFormController extends AbstractEntityListUIController<CommandeTitreTransport> implements Serializable {

	private static final long serialVersionUID = -2520806200426979462L;

	/*
	 * Services
	 */
	
	@Inject protected CommandeTitreTransportService commandeTitreTransportService;
	@Inject protected MissionExecuteeService missionExecuteeService;
	@Inject protected DemandeCotationMissionService demandeCotationMissionService;
	
	@Inject protected DemandeCotationMhciFormController demandeCotationMhciFormController;
	
	
	@Getter @Setter private Long numero;
	@Getter @Setter private String titleDemande;
	@Getter @Setter private PieceJustificativeUploader uploader=new PieceJustificativeUploader();
	@Getter @Setter private CommandeTitreTransport voir;
	@Getter @Setter MissionExecutee mission=new MissionExecutee();
	

	@Override
	protected void initialisation() {
		super.initialisation();
		try {
			numero = Long.parseLong(Faces.getRequestParameter(webConstantResources.getRequestParamDemandeCotation()));
			mission=missionExecuteeService.findById(demandeCotationMissionService.findById(numero).getMissionId());
		} catch (NumberFormatException e) {}
		
		title="Commande de billet d'avion";
		titleDemande="Demande de cotation";
		listTitle="Propositions de cotation des prestataires";
		selectLabel = "bouton.selectionner";
		defaultSubmitCommand.setRendered(false);
		
		demandeCotationMhciFormController.preRenderView();
		
		//showCheckBox = false;
		//showRadio = true;
		//showActionsColumn=false;
	}

	@Override
	protected List<CommandeTitreTransport> load() {
		return (List<CommandeTitreTransport>) commandeTitreTransportService.findATraiter(Code.NATURE_OPERATION_GENERATION_BCTT);
	}
	

	public String href(CommandeTitreTransport commande){
		return navigationManager.url("generer_commande_mhci_form", new Object[]{webConstantResources.getRequestParamDemandeCotation(),commande.getPrestataireDemandeCotationMission().getId().getDemandeCotationMissionId(),webConstantResources.getRequestParamPrestataire(),commande.getPrestataireDemandeCotationMission().getId().getPrestataireId(),webConstantResources.getRequestParamMission(),mission.getId(),webConstantResources.getRequestParamTabMenuItemIndex(), userSessionManager.getUiMenuTabPrestationCommandeMhciIndex()},false);
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
