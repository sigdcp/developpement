package ci.gouv.budget.solde.sigdcp.controller.dossier;

import java.io.Serializable;

import javax.inject.Inject;

import lombok.Getter;
import lombok.Setter;
import ci.gouv.budget.solde.sigdcp.controller.AbstractEntityFormUIController;
import ci.gouv.budget.solde.sigdcp.controller.AbstractFormSubmitAction;
import ci.gouv.budget.solde.sigdcp.controller.fichier.PieceJustificativeUploader;
import ci.gouv.budget.solde.sigdcp.model.dossier.Deplacement;
import ci.gouv.budget.solde.sigdcp.model.dossier.Dossier;
import ci.gouv.budget.solde.sigdcp.model.dossier.NatureDeplacement;
import ci.gouv.budget.solde.sigdcp.model.dossier.PieceJustificative;
import ci.gouv.budget.solde.sigdcp.model.dossier.PieceJustificativeAFournir;
import ci.gouv.budget.solde.sigdcp.service.dossier.AbstractDossierService;
import ci.gouv.budget.solde.sigdcp.service.dossier.NatureDeplacementService;
import ci.gouv.budget.solde.sigdcp.service.dossier.PieceJustificativeAFournirService;
import ci.gouv.budget.solde.sigdcp.service.dossier.PieceJustificativeService;
import ci.gouv.budget.solde.sigdcp.service.identification.AgentEtatService;

@Getter @Setter
public abstract class AbstractDossierUIControllerController<DOSSIER extends Dossier> extends AbstractEntityFormUIController<DOSSIER> implements Serializable {
	
	private static final long serialVersionUID = 6615049982603373278L;
	
	/*
	 * Services
	 */
	
	protected AbstractDossierService<DOSSIER> dossierService;
	@Inject protected NatureDeplacementService natureDeplacementService;
	@Inject protected PieceJustificativeService pieceJustificativeService; 
	@Inject protected PieceJustificativeAFournirService pieceJustificativeAFournirService;
	@Inject protected AgentEtatService agentEtatService;
	
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
	protected AbstractFormSubmitAction<DOSSIER> enregistrerPart1Action,enregistrerPart2Action,soumettreAction;
	
	@Override
	public void __firstPreRenderView__(){
		super.__firstPreRenderView__();
		entity.setBeneficiaire(agentEtatService.findAll().get(0));
		
		title = "Formulaire de : "+entity.getDeplacement().getNature().getLibelle();
		
		enregistrerPart1Action = new AbstractFormSubmitAction<DOSSIER>(entity,messageManager,"boutton.enregistrer","ui-icon-check","notification.demande.enregistree1",
				Boolean.FALSE,Boolean.TRUE,OUTCOME_CURRENT_VIEW) {
			private static final long serialVersionUID = -2683422739395829063L;
			@Override
			protected void action() {
				System.out.println("Enregistrer 1");
				//enregistrerPart1Action.setRendered(Boolean.FALSE);
				//enregistrerPart2Action.setRendered(Boolean.TRUE);
			}
		};
		
		enregistrerPart2Action = new AbstractFormSubmitAction<DOSSIER>(entity,messageManager,"boutton.enregistrer","ui-icon-check","notification.demande.enregistree2",
				Boolean.FALSE,Boolean.TRUE,OUTCOME_CURRENT_VIEW) {
			private static final long serialVersionUID = -2683422739395829063L;
			@Override
			protected void action() {
				System.out.println("Enregistrer 2");
				//enregistrerPart2Action.setRendered(Boolean.FALSE);
				//soumettreAction.setRendered(Boolean.TRUE);
			}
		};
		
		soumettreAction = new AbstractFormSubmitAction<DOSSIER>(entity,messageManager,"boutton.soumettre","ui-icon-check","notification.demande.soumise",
				Boolean.FALSE,Boolean.TRUE) {
			private static final long serialVersionUID = -2683422739395829063L;
			@Override
			protected void action() {
				System.out.println("Soumettre");
			}
		};
		
		System.out.println(getDossierService());
	}
	
	protected abstract AbstractDossierService<DOSSIER> getDossierService();
	
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
		
