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
import ci.gouv.budget.solde.sigdcp.model.prestation.DemandeCotationObseque;
import ci.gouv.budget.solde.sigdcp.model.prestation.PrestataireDemandeCotationObseque;
import ci.gouv.budget.solde.sigdcp.model.prestation.PrestataireDemandeCotationObsequeId;
import ci.gouv.budget.solde.sigdcp.service.prestation.DemandeCotationObsequeService;
import ci.gouv.budget.solde.sigdcp.service.prestation.PrestataireDemandeCotationObsequeService;

@Named @ViewScoped
public class CotationDemandeFoFormController extends AbstractEntityFormUIController<PrestataireDemandeCotationObseque> implements Serializable {

	private static final long serialVersionUID = -2520806200426979462L;

	/*
	 * Services
	 */
	
	@Inject protected PrestataireDemandeCotationObsequeService prestataireDemandeCotationObsequeService;
	@Inject protected DemandeCotationObsequeService demandeCotationObsequeService;
	@Inject @Getter protected PieceJustificativeUploader pieceJustificativeUploader=new PieceJustificativeUploader();
	

	PrestataireDemandeCotationObsequeId demandeId = new PrestataireDemandeCotationObsequeId();
	@Getter @Setter private Long numero;
	@Getter @Setter DemandeCotationObseque demandeCotation = new DemandeCotationObseque();	
	@Getter @Setter String listTitle;
	
	@Override
	protected void initialisation() {
		super.initialisation();

		try {
			numero = Long.parseLong(Faces.getRequestParameter(webConstantResources.getRequestParamDemandeCotation()));
			demandeId.setDemandeCotationObsequeId(numero);
			demandeId.setPrestataireId(userSessionManager.getCompteUtilisateur().getUtilisateur().getId());
		} catch (NumberFormatException e) {}
		
		
		entity = prestataireDemandeCotationObsequeService.nouveau(prestataireDemandeCotationObsequeService.findById(demandeId));
		
		//debug(entity.getClasseVoyages().iterator());
		title="Repondre à une demande de cotation de frais d'obsèque";
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
		
	}

	
	@Override
	protected void onDefaultSubmitAction() throws Exception {
		entity.setFichier(pieceJustificativeUploader.process().iterator().next().getFichier());
		prestataireDemandeCotationObsequeService.enregistrer(entity);
	}
}
