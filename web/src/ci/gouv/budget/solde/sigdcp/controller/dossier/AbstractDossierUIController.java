package ci.gouv.budget.solde.sigdcp.controller.dossier;

import java.io.Serializable;

import javax.inject.Inject;

import lombok.Getter;
import lombok.Setter;
import ci.gouv.budget.solde.sigdcp.controller.fichier.PieceJustificativeUploader;
import ci.gouv.budget.solde.sigdcp.controller.ui.form.AbstractEntityFormUIController;
import ci.gouv.budget.solde.sigdcp.controller.ui.form.command.Action;
import ci.gouv.budget.solde.sigdcp.controller.ui.form.command.FormCommand;
import ci.gouv.budget.solde.sigdcp.model.dossier.Deplacement;
import ci.gouv.budget.solde.sigdcp.model.dossier.Dossier;
import ci.gouv.budget.solde.sigdcp.model.dossier.NatureDeplacement;
import ci.gouv.budget.solde.sigdcp.model.dossier.PieceJustificative;
import ci.gouv.budget.solde.sigdcp.model.dossier.PieceJustificativeAFournir;
import ci.gouv.budget.solde.sigdcp.service.dossier.AbstractDossierService;
import ci.gouv.budget.solde.sigdcp.service.dossier.PieceJustificativeAFournirService;
import ci.gouv.budget.solde.sigdcp.service.dossier.PieceJustificativeService;

@Getter @Setter
public abstract class AbstractDossierUIController<DOSSIER extends Dossier,DOSSIER_SERVICE extends AbstractDossierService<DOSSIER>> extends AbstractEntityFormUIController<DOSSIER> implements Serializable {
	
	private static final long serialVersionUID = 6615049982603373278L;
	
	/*
	 * Services
	 */
	@Inject protected PieceJustificativeService pieceJustificativeService; 
	@Inject protected PieceJustificativeAFournirService pieceJustificativeAFournirService;
	
	/*
	 * DTOs
	 */
	
	@Inject @Getter protected PieceJustificativeUploader pieceJustificativeUploader;
	
	
	/*
	 * Paramètres de requete
	 */
	@Setter @Getter protected NatureDeplacement natureDaplacement;
	
	/*
	 * Actions
	 */
	protected FormCommand<DOSSIER> enregistrerCommand;
	
	@Override
	protected void initialisation() {
		super.initialisation();
		//entity.setBeneficiaire((AgentEtat) userSessionManager.getUser());
		title = "Formulaire de : "+entity.getDeplacement().getNature().getLibelle();
		
		defaultSubmitCommand.setValue(text("bouton.soumettre"));
		defaultSubmitCommand.setNotificationMessageId("notification.demande.soumise");
		defaultSubmitCommand.setAjax(Boolean.FALSE);
		
		enregistrerCommand = createCommand().init("bouton.enregistrer","ui-icon-check","notification.demande.enregistree1", new Action() {
			private static final long serialVersionUID = 1L;
			@Override
			protected Object __execute__(Object object) throws Exception {
				getDossierService().enregistrer(entity, pieceJustificativeUploader.process());
				return null;
			}
		}).onSuccessStayOnCurrentView();
		enregistrerCommand.setAjax(Boolean.FALSE);
		
		//just for testing
		//defaultSubmitCommand.setImmediate(Boolean.TRUE);
		//enregistrerCommand.setImmediate(Boolean.TRUE);
		
	}
	
	protected abstract DOSSIER_SERVICE getDossierService();
	
	@Override
	protected void initCreateOperation() {
		super.initCreateOperation();
		entity.setDeplacement(createDeplacement());
		entity.getDeplacement().setNature(natureDaplacement);
		for(PieceJustificativeAFournir pieceJustificativeAFournir : pieceJustificativeAFournirService.findByNatureDeplacementId(entity.getDeplacement().getNature().getCode()))
			pieceJustificativeUploader.addPieceJustificative(new PieceJustificative(pieceJustificativeAFournir, entity));
	}
	
	@Override
	protected void onDefaultSubmitAction() throws Exception {
		getDossierService().soumettre(entity, pieceJustificativeUploader.process());
	}
	
	protected Deplacement createDeplacement(){
		return new Deplacement();
	}
	
	public DOSSIER getDossier(){
		return entity;
	}
	
	
	
		
}
		
