package ci.gouv.budget.solde.sigdcp.controller.prestation;

import java.io.Serializable;
import java.util.Arrays;

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
import ci.gouv.budget.solde.sigdcp.model.prestation.Facture;
import ci.gouv.budget.solde.sigdcp.service.prestation.CommandeService;
import ci.gouv.budget.solde.sigdcp.service.prestation.FactureService;

@Named @ViewScoped
public class ReceptionFactureDefinitiveFormController extends AbstractEntityFormUIController<Facture> implements Serializable {

	private static final long serialVersionUID = -2520806200426979462L;

	/*
	 * Services
	 */
	
	@Inject protected FactureService factureService;
	@Inject protected CommandeService commandeService;
	@Inject @Getter protected PieceJustificativeUploader pieceJustificativeUploader=new PieceJustificativeUploader();
	

	@Getter @Setter private Long numero;
	@Getter @Setter private PieceJustificative pieceJustificative;
	
	@Override
	protected void initialisation() {
		super.initialisation();

		try {
			numero = Long.parseLong(Faces.getRequestParameter(webConstantResources.getRequestParamCommande()));
		} catch (NumberFormatException e) {}
		
		
		entity = factureService.nouveau(commandeService.findById(numero));
		
		//debug(entity.getClasseVoyages().iterator());
		title="Reception de facture définitive";
		
		defaultSubmitCommand.setValue(text("bouton.enregistrer"));
		defaultSubmitCommand.setAjax(false);
		//pieceJustificativeUploader.setShowInputs(false);
		pieceJustificativeUploader.setTitle("");
		
		TypePieceJustificative tp = new TypePieceJustificative("01", "Fichier facture définitive");
		PieceJustificativeAFournir p = new PieceJustificativeAFournir();
		p.setTypePieceJustificative(tp);
		p.setConfig(new PieceJustificativeAFournirConfig());
		
		pieceJustificativeUploader.addPieceJustificative(pieceJustificative=new PieceJustificative(null, p), true, false);
		pieceJustificativeUploader.update();
		
	}

	
	@Override
	protected void onDefaultSubmitAction() throws Exception {
		entity.setFichier(pieceJustificativeUploader.process().iterator().next().getFichier());
		entity.setNumero(pieceJustificative.getNumero());
		entity.setDateEtablissement(pieceJustificative.getDateEtablissement());
		factureService.valider(Arrays.asList(entity));
	}
}
