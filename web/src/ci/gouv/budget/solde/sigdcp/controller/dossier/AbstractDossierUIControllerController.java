package ci.gouv.budget.solde.sigdcp.controller.dossier;

import java.io.Serializable;
import java.util.Collection;

import javax.inject.Inject;

import lombok.Getter;
import lombok.Setter;

import org.omnifaces.util.Faces;

import ci.gouv.budget.solde.sigdcp.controller.AbstractUIFormController;
import ci.gouv.budget.solde.sigdcp.controller.ConstantResources;
import ci.gouv.budget.solde.sigdcp.controller.fichier.PieceJustificativeUploader;
import ci.gouv.budget.solde.sigdcp.model.dossier.Deplacement;
import ci.gouv.budget.solde.sigdcp.model.dossier.Dossier;
import ci.gouv.budget.solde.sigdcp.model.dossier.PieceJustificative;
import ci.gouv.budget.solde.sigdcp.model.dossier.PieceJustificativeAFournir;
import ci.gouv.budget.solde.sigdcp.model.identification.AgentEtat;
import ci.gouv.budget.solde.sigdcp.service.dossier.AbstractDossierService;
import ci.gouv.budget.solde.sigdcp.service.dossier.NatureDeplacementService;
import ci.gouv.budget.solde.sigdcp.service.dossier.PieceJustificativeAFournirService;
import ci.gouv.budget.solde.sigdcp.service.dossier.PieceJustificativeService;
import ci.gouv.budget.solde.sigdcp.service.identification.AgentEtatService;

@Getter @Setter
public abstract class AbstractDossierUIControllerController<DOSSIER extends Dossier> extends AbstractUIFormController implements Serializable {
	
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
	
	@Getter protected DOSSIER dossier;
	@Inject @Getter protected PieceJustificativeUploader pieceJustificativeUploader;
	private AgentEtat agentEtat;
	
	@Getter @Setter private Boolean soumis = Boolean.FALSE;
	@Setter @Getter PieceJustificative pieceJustificativeSelectionne;
	/*
	 * Paramètres de requete
	 */
	@Setter @Getter protected String natureDaplacementCode;
	@Setter @Getter protected String dossierCode;
	
	/*
	 * Constantes de la couche web
	 */
	@Inject protected ConstantResources constantResources;
	
	protected void postConstruct(){
		super.postConstruct();
		agentEtat = agentEtatService.findAll().get(0);
		natureDaplacementCode = Faces.getRequestParameter(constantResources.getRequestParamNatureDeplacement());
		
		String action = Faces.getRequestParameter(constantResources.getRequestParamAction());
		if(constantResources.getRequestParamActionEditer().equals(action))
			editable = Boolean.TRUE;
		dossierCode = Faces.getRequestParameter(constantResources.getRequestParamEntityId());
		Boolean load;
		if(Boolean.TRUE.equals(editable)){
			if(dossierCode==null){
				load = Boolean.FALSE;
				dossier = createDossierInstance();
				dossier.setDeplacement(new Deplacement());
				dossier.getDeplacement().setNature(natureDeplacementService.findById(natureDaplacementCode));
				for(PieceJustificativeAFournir pieceJustificativeAFournir : pieceJustificativeAFournirService.findByNatureDeplacementId(natureDaplacementCode))
					pieceJustificativeUploader.addPieceJustificative(new PieceJustificative(pieceJustificativeAFournir, dossier));
			}else
				load = Boolean.TRUE;
		}else
			load = Boolean.TRUE;
		
		if(load){
			/*
			dossier = getDossierService().findById(dossierCode); 
			Collection<PieceJustificative> pjs = pieceJustificativeService.findByDossier(dossier);
			for(PieceJustificative pieceJustificative : pjs)
				pieceJustificativeUploader.addPieceJustificative(pieceJustificative);
				*/
			// TODO a supprimer
			dossier = createDossierInstance();
			natureDaplacementCode = "AFF";
			dossier.setDeplacement(new Deplacement());
			dossier.getDeplacement().setNature(natureDeplacementService.findById(natureDaplacementCode));
			for(PieceJustificativeAFournir pieceJustificativeAFournir : pieceJustificativeAFournirService.findByNatureDeplacementId(natureDaplacementCode))
				pieceJustificativeUploader.addPieceJustificative(new PieceJustificative(pieceJustificativeAFournir, dossier));
		}
		
		title = "Formulaire de : "+dossier.getDeplacement().getNature().getLibelle();
	}
	
	protected abstract DOSSIER createDossierInstance();
	
	protected abstract AbstractDossierService<DOSSIER> getDossierService();
		
}
		
