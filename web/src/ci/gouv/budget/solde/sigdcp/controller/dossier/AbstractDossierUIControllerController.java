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
import ci.gouv.budget.solde.sigdcp.model.identification.AgentEtat;
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
	
	@Inject protected NatureDeplacementService natureDeplacementService;
	@Inject protected PieceJustificativeService pieceJustificativeService; 
	@Inject protected PieceJustificativeAFournirService pieceJustificativeAFournirService;
	@Inject protected AgentEtatService agentEtatService;
	
	/*
	 * DTOs
	 */
	
	@Inject @Getter protected PieceJustificativeUploader pieceJustificativeUploader;
	protected AgentEtat agentEtat;
	
	@Getter @Setter protected Boolean soumis = Boolean.FALSE;
	@Setter @Getter protected PieceJustificative pieceJustificativeSelectionne;
	
	/*
	 * Paramètres de requete
	 */
	@Setter @Getter protected NatureDeplacement natureDaplacement;
	
	/*
	 * Actions
	 */
	private AbstractFormSubmitAction enregistrerPart1Action,enregistrerPart2Action,soumettreAction;
	
	@Override
	public void __firstPreRenderView__(){
		super.__firstPreRenderView__();
		agentEtat = agentEtatService.findAll().get(0);
		/*if(load){
			
			dossier = getDossierService().findById(dossierCode); 
			Collection<PieceJustificative> pjs = pieceJustificativeService.findByDossier(dossier);
			for(PieceJustificative pieceJustificative : pjs)
				pieceJustificativeUploader.addPieceJustificative(pieceJustificative);
				*/
			// TODO a supprimer
			//dossier = createDossierInstance();
			/*
			natureDaplacementCode = "AFF";
			entity.setDeplacement(new Deplacement());
			entity.getDeplacement().setNature(natureDeplacementService.findById(natureDaplacementCode));
			for(PieceJustificativeAFournir pieceJustificativeAFournir : pieceJustificativeAFournirService.findByNatureDeplacementId(natureDaplacementCode))
				pieceJustificativeUploader.addPieceJustificative(new PieceJustificative(pieceJustificativeAFournir, entity));
			
		}*/

		title = "Formulaire de : "+entity.getDeplacement().getNature().getLibelle();
		
		enregistrerPart1Action = new AbstractFormSubmitAction("Enregistrer 1") {
			private static final long serialVersionUID = -2683422739395829063L;
			@Override
			protected void action() {
				System.out.println("Enregistrer 1");
			}
		};
		
		enregistrerPart2Action = new AbstractFormSubmitAction("Enregistrer 2") {
			private static final long serialVersionUID = -2683422739395829063L;
			@Override
			protected void action() {
				System.out.println("Enregistrer 2");
			}
		};
		
		soumettreAction = new AbstractFormSubmitAction("Soumettre") {
			private static final long serialVersionUID = -2683422739395829063L;
			@Override
			protected void action() {
				System.out.println("Soumettre");
			}
		};
	}
		
	protected abstract AbstractDossierService<DOSSIER> getDossierService();
	
	@Override
	protected void initCreateOperation() {
		super.initCreateOperation();
		entity.setDeplacement(new Deplacement());
		entity.getDeplacement().setNature(natureDaplacement);
		for(PieceJustificativeAFournir pieceJustificativeAFournir : pieceJustificativeAFournirService.findByNatureDeplacementId(entity.getDeplacement().getNature().getCode()))
			pieceJustificativeUploader.addPieceJustificative(new PieceJustificative(pieceJustificativeAFournir, entity));
	}
	
	public DOSSIER getDossier(){
		return entity;
	}
	
		
}
		
