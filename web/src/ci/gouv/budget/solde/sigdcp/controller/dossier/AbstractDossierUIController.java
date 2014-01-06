package ci.gouv.budget.solde.sigdcp.controller.dossier;

import java.io.Serializable;

import javax.inject.Inject;

import lombok.Getter;
import lombok.Setter;
import ci.gouv.budget.solde.sigdcp.controller.AbstractEntityFormUIController;
import ci.gouv.budget.solde.sigdcp.controller.AbstractFormSubmitAction;
import ci.gouv.budget.solde.sigdcp.controller.NavigationManager;
import ci.gouv.budget.solde.sigdcp.controller.fichier.PieceJustificativeUploader;
import ci.gouv.budget.solde.sigdcp.model.dossier.Deplacement;
import ci.gouv.budget.solde.sigdcp.model.dossier.Dossier;
import ci.gouv.budget.solde.sigdcp.model.dossier.NatureDeplacement;
import ci.gouv.budget.solde.sigdcp.model.dossier.PieceJustificative;
import ci.gouv.budget.solde.sigdcp.model.dossier.PieceJustificativeAFournir;
import ci.gouv.budget.solde.sigdcp.model.identification.AgentEtat;
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
	@Setter @Getter protected PieceJustificative pieceJustificativeSelectionne;
	
	/*
	 * Paramètres de requete
	 */
	@Setter @Getter protected NatureDeplacement natureDaplacement;
	
	/*
	 * Actions
	 */
	protected AbstractFormSubmitAction<DOSSIER> enregistrerAction;
	
	@Override
	public void __firstPreRenderView__(){
		super.__firstPreRenderView__();
		
		entity.setBeneficiaire((AgentEtat) userSessionManager.getUser());
		
		title = "Formulaire de : "+entity.getDeplacement().getNature().getLibelle();
		
		enregistrerAction = new AbstractFormSubmitAction<DOSSIER>(entity,messageManager,"boutton.enregistrer","ui-icon-check","notification.demande.enregistree1",
				Boolean.FALSE,Boolean.TRUE,NavigationManager.OUTCOME_CURRENT_VIEW) {
			private static final long serialVersionUID = -2683422739395829063L;
			@Override
			protected void action() {
				try {
					getDossierService().enregistrer(entity, pieceJustificativeUploader.process());
				} catch (Exception e) {
					throw new RuntimeException(e);
				} 
			}
		};
		
		
		defaultSubmitAction = new AbstractFormSubmitAction<DOSSIER>(entity,messageManager,"boutton.soumettre","ui-icon-check","notification.demande.soumise",
				Boolean.FALSE,Boolean.TRUE) {
			private static final long serialVersionUID = -2683422739395829063L;
			@Override
			protected void action() {
				try {
					getDossierService().soumettre(entity, pieceJustificativeUploader.process());
				} catch (Exception e) {
					throw new RuntimeException(e);
				} 
			}
		};
		
		//just for testing
		defaultSubmitAction.setImmediate(Boolean.TRUE);
		enregistrerAction.setImmediate(Boolean.TRUE);
		
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
	
	protected Deplacement createDeplacement(){
		return new Deplacement();
	}
	
	public DOSSIER getDossier(){
		return entity;
	}
	
		
}
		
