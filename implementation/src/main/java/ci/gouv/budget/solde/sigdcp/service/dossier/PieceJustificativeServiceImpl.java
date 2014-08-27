package ci.gouv.budget.solde.sigdcp.service.dossier;

import java.io.Serializable;

import javax.inject.Inject;

import ci.gouv.budget.solde.sigdcp.dao.dossier.PieceJustificativeDao;
import ci.gouv.budget.solde.sigdcp.model.dossier.PieceJustificative;

public class PieceJustificativeServiceImpl extends AbstractDocumentServiceImpl<PieceJustificative> implements PieceJustificativeService , Serializable {

	private static final long serialVersionUID = -7601857525393731774L;
	
	//@Inject protected PieceJustificativeAFournirDao pieceJustificativeAFournirDao;
	//@Inject protected DossierDao dossierDao;
	
	@Inject
	public PieceJustificativeServiceImpl(PieceJustificativeDao dao) {
		super(dao);
	}
	/*
	@Override
	public Collection<PieceJustificative> findByDossier(Dossier dossier,Collection<PieceJustificative> pieceJustificatives,Map<String, Object> parametres) throws ServiceException {
		//Dossier dossierEnCoursSaisie = dossierDao.readSaisieByPersonneByNatureDeplacement((AgentEtat) userSessionManager.getUser(), natureDaplacement);
		
		//if(Code.NATURE_DEPLACEMENT_TRANSIT_BAGAGGES.equals(dossier.getDeplacement().getNature().getCode()))
		
		if(!Code.CATEGORIE_DEPLACEMENT_DEFINITIF.equals(dossier.getDeplacement().getNature().getCategorie().getCode()) || pieceJustificatives==null){
			
			//quelles sont les pieces a fournir
			//System.out.println(dossier.getDeplacement().getNature().getCode()+" "+typeDepenseId);
			//System.out.println(ToStringBuilder.reflectionToString(dossier.getDeplacement(), ToStringStyle.MULTI_LINE_STYLE));
			Collection<PieceJustificativeAFournir> pieceJustificativeAFournirs = pieceJustificativeAFournirDao.readBaseByNatureDeplacementIdByTypeDepenseId(dossier.getDeplacement().getNature().getCode(),
					dossier.getDeplacement().getTypeDepense().getCode());
			
			//System.out.println("PJF : "+pieceJustificativeAFournirs);
			//quelles sont les pieces fournis
			Collection<PieceJustificative> pieceJustificativesFournis;
			if(StringUtils.isNotEmpty(dossier.getNumero()))
				pieceJustificativesFournis = ((PieceJustificativeDao)dao).readByDossier(dossier);
			else
				pieceJustificativesFournis = new LinkedList<>();
			
			//croisement
			for(PieceJustificativeAFournir pieceJustificativeAFournir : pieceJustificativeAFournirs){
				boolean trouve = false;
				for(PieceJustificative pieceJustificative : pieceJustificativesFournis)
					if(pieceJustificative.getModel().equals(pieceJustificativeAFournir)){
						trouve = true;
						break;
					}
				if(!trouve)
					pieceJustificativesFournis.add(new PieceJustificative(pieceJustificativeAFournir));
			}
			return pieceJustificativesFournis;
			
		}
		
		Collection<PieceJustificative> newPieceJustificatives = new LinkedList<>();
		//toutes les pieces de base reste par defaut
		for(PieceJustificative pieceJustificative : pieceJustificatives)
			if(Boolean.FALSE.equals(pieceJustificative.getModel().getConfig().getConditionnee()) )
				newPieceJustificatives.add(pieceJustificative);
		
		PieceJustificativeAFournir modelPiece = null;
		
		
		return newPieceJustificatives;
	}*/
	/*
	private Collection<PieceJustificative> transit(Boolean mae,String typeDepenseId){
		Collection<PieceJustificative> pieceJustificatives = new LinkedList<>();
		if(mae){
			pieceJustificatives.add(new PieceJustificative(pieceJustificativeAFournirDao.readByNatureDeplacementIdByTypePieceIdByTypeDepenseId(
					Code.NATURE_DEPLACEMENT_TRANSIT_BAGAGGES, Code.TYPE_PIECE_DECISION_RAPPEL, )))
		}else{
			
		}
		return pieceJustificatives;
	}*/
	
	/*
	@Override
	public Map<String, Object> findParametresByDossier(Dossier dossier,Collection<PieceJustificative> pieceJustificatives)throws ServiceException {
		Map<String, Object> parametres = new HashMap<String, Object>();
		PieceJustificativeAFournir modelPiece = null;
		boolean dossierPersiste = dossierDao.exist(dossier.getNumero());
		//System.out.println(dossierPersiste);
		//Extrait de mariage
		parametres.put(constantResources.getFormParamMarie(), dossierPersiste?false:null);
		modelPiece = pieceJustificativeAFournirDao.readByNatureDeplacementIdByTypePieceIdByTypeDepenseId(dossier.getDeplacement().getNature().getCode(), Code.TYPE_PIECE_EXTRAIT_MARIAGE,
				dossier.getDeplacement().getTypeDepense().getCode());
		for(PieceJustificative pj : pieceJustificatives)
			if(pj.getModel().equals(modelPiece)){
				parametres.put(constantResources.getFormParamMarie(), true);
				break;
			}
		//nombre d'enfant
		int ne = 0;
		modelPiece = pieceJustificativeAFournirDao.readByNatureDeplacementIdByTypePieceIdByTypeDepenseId(dossier.getDeplacement().getNature().getCode(), Code.TYPE_PIECE_EXTRAIT_NAISSANCE,
				dossier.getDeplacement().getTypeDepense().getCode());
		for(PieceJustificative pj : pieceJustificatives)
			if(pj.getModel().equals(modelPiece))
				ne++;
		parametres.put(constantResources.getFormParamNombreEnfant(), ne);
		return parametres;
	}*/

}
