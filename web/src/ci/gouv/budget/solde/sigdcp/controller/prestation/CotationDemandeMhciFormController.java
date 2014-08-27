package ci.gouv.budget.solde.sigdcp.controller.prestation;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import lombok.Getter;
import lombok.Setter;

import org.omnifaces.util.Faces;

import ci.gouv.budget.solde.sigdcp.controller.fichier.PieceJustificativeUploader;
import ci.gouv.budget.solde.sigdcp.controller.ui.form.AbstractEntityFormUIController;
import ci.gouv.budget.solde.sigdcp.model.dossier.PieceJustificative;
import ci.gouv.budget.solde.sigdcp.model.dossier.PieceJustificativeAFournir;
import ci.gouv.budget.solde.sigdcp.model.dossier.PieceJustificativeAFournirConfig;
import ci.gouv.budget.solde.sigdcp.model.dossier.TypePieceJustificative;
import ci.gouv.budget.solde.sigdcp.model.prestation.DemandeCotationMission;
import ci.gouv.budget.solde.sigdcp.model.prestation.PrestataireDemandeCotationMission;
import ci.gouv.budget.solde.sigdcp.model.prestation.PrestataireDemandeCotationMissionId;
import ci.gouv.budget.solde.sigdcp.service.prestation.DemandeCotationMissionService;
import ci.gouv.budget.solde.sigdcp.service.prestation.PrestataireDemandeCotationMissionService;

@Named @ViewScoped
public class CotationDemandeMhciFormController extends AbstractEntityFormUIController<PrestataireDemandeCotationMission> implements Serializable {

	private static final long serialVersionUID = -2520806200426979462L;

	/*
	 * Services
	 */
	
	@Inject protected PrestataireDemandeCotationMissionService prestataireDemandeCotationMissionService;
	@Inject protected DemandeCotationMissionService demandeCotationMissionService;
	@Inject @Getter protected PieceJustificativeUploader pieceJustificativeUploader=new PieceJustificativeUploader();
	

	PrestataireDemandeCotationMissionId demandeId = new PrestataireDemandeCotationMissionId();
	@Getter @Setter private Long numero;
	@Getter @Setter DemandeCotationMission demandeCotation = new DemandeCotationMission();	
	@Getter @Setter String listTitle;
	
	@Override
	protected void initialisation() {
		super.initialisation();

		try {
			numero = Long.parseLong(Faces.getRequestParameter(webConstantResources.getRequestParamDemandeCotation()));
			demandeId.setDemandeCotationMissionId(numero);
			demandeId.setPrestataireId(userSessionManager.getCompteUtilisateur().getUtilisateur().getId());
		} catch (NumberFormatException e) {}
		
		
		entity = prestataireDemandeCotationMissionService.nouveau(prestataireDemandeCotationMissionService.findById(demandeId));
		
		//debug(entity.getClasseVoyages().iterator());
		title="Repondre à une demande de cotation";
		listTitle="Demande de cotation N°"+numero;
		
		defaultSubmitCommand.setValue(text("bouton.soumettre"));
		defaultSubmitCommand.setAjax(false);
		pieceJustificativeUploader.setShowInputs(false);
		pieceJustificativeUploader.setTitle("");
		
		TypePieceJustificative tp = new TypePieceJustificative("01", "Fichier de cotation");
		PieceJustificativeAFournir p = new PieceJustificativeAFournir();
		p.setTypePieceJustificative(tp);
		p.setConfig(new PieceJustificativeAFournirConfig());
		
		pieceJustificativeUploader.addPieceJustificative(new PieceJustificative(null, p), true, false);
		pieceJustificativeUploader.update();
		
		defaultSubmitCommand.onSuccessGoTo(navigationManager.url("demande_cotation_mhci_liste",false), "", null);
		
	}

	
	@Override
	protected void onDefaultSubmitAction() throws Exception {
		entity.setFichier(pieceJustificativeUploader.process().iterator().next().getFichier());
		prestataireDemandeCotationMissionService.enregistrer(entity);
	}
}
