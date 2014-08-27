package ci.gouv.budget.solde.sigdcp.controller.calendrier;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import lombok.Getter;
import ci.gouv.budget.solde.sigdcp.controller.fichier.PieceJustificativeUploader;
import ci.gouv.budget.solde.sigdcp.controller.ui.form.AbstractEntityFormUIController;
import ci.gouv.budget.solde.sigdcp.model.calendrier.LettreAvance;
import ci.gouv.budget.solde.sigdcp.model.dossier.PieceJustificative;
import ci.gouv.budget.solde.sigdcp.model.dossier.PieceJustificativeAFournir;
import ci.gouv.budget.solde.sigdcp.model.dossier.PieceJustificativeAFournirConfig;
import ci.gouv.budget.solde.sigdcp.model.dossier.TypePieceJustificative;
import ci.gouv.budget.solde.sigdcp.service.calendrier.LettreAvanceService;
import ci.gouv.budget.solde.sigdcp.service.resources.CRUDType;

@Named @ViewScoped
public class LettreAvanceController extends AbstractEntityFormUIController<LettreAvance> implements Serializable {

	private static final long serialVersionUID = -8293772128779434233L;
	
	@Inject LettreAvanceService lettreAvanceService;
	
	@Inject @Getter protected PieceJustificativeUploader pieceJustificativeUploader=new PieceJustificativeUploader();

	@Override
	protected void initialisation() {
		crudType=CRUDType.CREATE;
		super.initialisation();
		title="Lettre d'avance";
		defaultSubmitCommand.setAjax(false);
		defaultSubmitCommand.setValue(text("bouton.enregistrer"));
		
		//pieceJustificativeUploader.setShowInputs(false);
		pieceJustificativeUploader.setTitle("Pièce justificative (obligatoire)");
		
		TypePieceJustificative tp = new TypePieceJustificative("01", "Fichier de la lettre d'avance");
		PieceJustificativeAFournir p = new PieceJustificativeAFournir();
		p.setTypePieceJustificative(tp);
		p.setConfig(new PieceJustificativeAFournirConfig());
		
		pieceJustificativeUploader.addPieceJustificative(new PieceJustificative(null, p), true, false);
		pieceJustificativeUploader.update();
	}
	
	@Override
	protected void onDefaultSubmitAction() throws Exception {
		entity.setNumero(pieceJustificativeUploader.process().iterator().next().getNumero());
		entity.setDateEtb(pieceJustificativeUploader.process().iterator().next().getDateEtablissement());
		entity.setFichier(pieceJustificativeUploader.process().iterator().next().getFichier());
		lettreAvanceService.enregistrer(entity);
	}
	
}
