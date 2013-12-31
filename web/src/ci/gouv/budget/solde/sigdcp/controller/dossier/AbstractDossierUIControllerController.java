package ci.gouv.budget.solde.sigdcp.controller.dossier;

import java.io.Serializable;

import javax.inject.Inject;

import lombok.Getter;
import lombok.Setter;

import org.omnifaces.util.Faces;

import ci.gouv.budget.solde.sigdcp.controller.AbstractEntityFormUIController;
import ci.gouv.budget.solde.sigdcp.controller.fichier.PieceJustificativeUploader;
import ci.gouv.budget.solde.sigdcp.model.dossier.Deplacement;
import ci.gouv.budget.solde.sigdcp.model.dossier.Dossier;
import ci.gouv.budget.solde.sigdcp.model.dossier.DossierDD;
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
	
	@Override
	public void __firstPreRenderView__(){
		System.out.println("Param Nature : "+natureDaplacement);
		System.out.println("Param Dossier : "+entity);
		agentEtat = agentEtatService.findAll().get(0);
		//natureDaplacementCode = Faces.getRequestParameter(constantResources.getRequestParamNatureDeplacement());
		
		String action = Faces.getRequestParameter(constantResources.getRequestParamAction());
		if(constantResources.getRequestParamActionEditer().equals(action))
			editable = Boolean.TRUE;
		
		//dossierCode = Faces.getRequestParameter(constantResources.getRequestParamEntityId());
		Boolean load;
		if(isCreate()){
			if(entity==null){
				System.out
						.println("AbstractDossierUIControllerController.__firstPreRenderView__()");
				load = Boolean.FALSE;
				entity = (DOSSIER) new DossierDD(); //createDossierInstance();
				entity.setDeplacement(new Deplacement());
				entity.getDeplacement().setNature(natureDaplacement);
				for(PieceJustificativeAFournir pieceJustificativeAFournir : pieceJustificativeAFournirService.findByNatureDeplacementId(entity.getDeplacement().getNature().getCode()))
					pieceJustificativeUploader.addPieceJustificative(new PieceJustificative(pieceJustificativeAFournir, entity));
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
			//dossier = createDossierInstance();
			/*
			natureDaplacementCode = "AFF";
			entity.setDeplacement(new Deplacement());
			entity.getDeplacement().setNature(natureDeplacementService.findById(natureDaplacementCode));
			for(PieceJustificativeAFournir pieceJustificativeAFournir : pieceJustificativeAFournirService.findByNatureDeplacementId(natureDaplacementCode))
				pieceJustificativeUploader.addPieceJustificative(new PieceJustificative(pieceJustificativeAFournir, entity));
			*/
		}
		System.out.println(entity);
		System.out.println(entity.getDeplacement());
		System.out.println(entity.getDeplacement().getNature());
		title = "Formulaire de : "+entity.getDeplacement().getNature().getLibelle();
	}
		
	protected abstract AbstractDossierService<DOSSIER> getDossierService();
	
	public DOSSIER getDossier(){
		return entity;
	}
	
		
}
		
