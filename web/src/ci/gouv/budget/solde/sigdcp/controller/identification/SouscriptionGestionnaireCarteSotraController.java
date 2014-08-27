package ci.gouv.budget.solde.sigdcp.controller.identification;


import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import lombok.Getter;
import ci.gouv.budget.solde.sigdcp.controller.application.UserSessionManager;
import ci.gouv.budget.solde.sigdcp.controller.fichier.PieceJustificativeUploader;
import ci.gouv.budget.solde.sigdcp.controller.ui.form.AbstractEntityFormUIController;
import ci.gouv.budget.solde.sigdcp.model.identification.AgentEtat;
import ci.gouv.budget.solde.sigdcp.model.identification.souscription.SouscriptionGestionnaireCarteSotra;
 
@Named @ViewScoped
public class SouscriptionGestionnaireCarteSotraController extends AbstractEntityFormUIController<SouscriptionGestionnaireCarteSotra> implements Serializable {
	
	private static final long serialVersionUID = -611561465509440427L;
	
	/*
	 * Services
	 */  
	//@Inject private DossierDDService dossierDDService;
	

	@Getter
	private PieceJustificativeUploader pieceJustificativeUploader;
	
	@Inject private UserSessionManager sessionManager;
	
	@Override
	public void initialisation() {
		super.initialisation();
		title = "Inscription des candidats gestionnaires";
		internalCode = "FS_Sotra_02_Ecran_01";
		defaultSubmitCommand.setValue(messageManager.getTextService().find("bouton.enregistrer"));
		defaultSubmitCommand.setAjax(false);
		
		

		
	}
	
	@Override
	protected void initCreateOperation() {
		super.initCreateOperation();
		entity.setGestionnaire((AgentEtat) sessionManager.getUser());
		//entity.getGestionnaire().getPieceIdentite().setModel(new PieceJustificativeAFournir(null, null, null, new TypePieceJustificative(Code.TYPE_PIECE_CNI, "Pièce d'identité"), 1, true, 1, false));
		//entity.getDecretCreationSection().setModel(new PieceJustificativeAFournir(null, null, null, new TypePieceJustificative(Code.TYPE_PIECE_CNI, "Décret de création du service"), 1, true, 1, false));
		//entity.getNoteService().setModel(new PieceJustificativeAFournir(null, null, null, new TypePieceJustificative(Code.TYPE_PIECE_CNI, "Note de service"), 1, true, 1, false));
		
		pieceJustificativeUploader = new PieceJustificativeUploader();
		pieceJustificativeUploader.setShowInputs(Boolean.FALSE);
		
		//pieceJustificativeUploader.addPieceJustificative(entity.getGestionnaire().getPieceIdentite());
		//pieceJustificativeUploader.addPieceJustificative(entity.getDecretCreationSection());
		//pieceJustificativeUploader.addPieceJustificative(entity.getNoteService());
	}
	

	
}
