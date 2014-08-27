package ci.gouv.budget.solde.sigdcp.controller.prestation;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import ci.gouv.budget.solde.sigdcp.controller.fichier.PieceJustificativeUploader;
import ci.gouv.budget.solde.sigdcp.controller.ui.form.AbstractEntityFormUIController;
import ci.gouv.budget.solde.sigdcp.model.dossier.Dossier;
import ci.gouv.budget.solde.sigdcp.model.dossier.DossierTransit;
import ci.gouv.budget.solde.sigdcp.model.prestation.Facture;

@Named @ViewScoped
public class FactureTransitBagageController extends AbstractEntityFormUIController<DossierTransit> implements Serializable {

	private static final long serialVersionUID = -2494512246140789877L;
	
	/*
	 * Services
	 */ 
	
	
	/* 
	 * Dtos 
	 */
	
	private PieceJustificativeUploader pieceJustificativeUploader;
	
	@Override
	protected void initialisation() {
		super.initialisation();
		title="Facture définitive de transit de bagage";
		internalCode="FS_TRANSIT_07_Ecran_01";
		defaultSubmitCommand.setValue(text("bouton.soumettre"));
		pieceJustificativeUploader = new PieceJustificativeUploader();
		
	}
	
}
