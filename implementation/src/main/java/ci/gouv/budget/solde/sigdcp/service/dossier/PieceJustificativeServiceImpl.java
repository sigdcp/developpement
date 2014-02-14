package ci.gouv.budget.solde.sigdcp.service.dossier;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import ci.gouv.budget.solde.sigdcp.dao.dossier.DossierDao;
import ci.gouv.budget.solde.sigdcp.dao.dossier.PieceJustificativeAFournirDao;
import ci.gouv.budget.solde.sigdcp.dao.dossier.PieceJustificativeDao;
import ci.gouv.budget.solde.sigdcp.model.Code;
import ci.gouv.budget.solde.sigdcp.model.dossier.Dossier;
import ci.gouv.budget.solde.sigdcp.model.dossier.PieceJustificative;
import ci.gouv.budget.solde.sigdcp.model.dossier.PieceJustificativeAFournir;
import ci.gouv.budget.solde.sigdcp.service.ServiceException;

@Stateless
public class PieceJustificativeServiceImpl extends AbstractDocumentServiceImpl<PieceJustificative> implements PieceJustificativeService , Serializable {

	private static final long serialVersionUID = -7601857525393731774L;
	
	@Inject protected PieceJustificativeAFournirDao pieceJustificativeAFournirDao;
	@Inject protected DossierDao dossierDao;
	
	@Inject
	public PieceJustificativeServiceImpl(PieceJustificativeDao dao) {
		super(dao);
	}
	/*
	@Override
	public Collection<PieceJustificative> findByDossier(Dossier dossier) {
		return ((PieceJustificativeDao)dao).readByDossier(dossier);
	}

	@Override
	public Collection<PieceJustificative> findByDossierByType(Dossier dossier,TypePieceJustificative type) {
		return ((PieceJustificativeDao)dao).readByDossierByType(dossier, type);
	}*/
	/*
	public Collection<PieceJustificative> findByNatureDeplacement(NatureDeplacement natureDeplacement) throws ServiceException {
		Collection<PieceJustificative> pieceJustificatives = new LinkedList<>();
		for(PieceJustificativeAFournir pieceJustificativeAFournir : pieceJustificativeAFournirDao.readBaseByNatureDeplacementId(natureDeplacement.getCode()))
			pieceJustificatives.add(new PieceJustificative(pieceJustificativeAFournir));
		return pieceJustificatives;
	}
	*/
	@Override
	public Collection<PieceJustificative> findByDossierByTypeDepenseId(Dossier dossier,String typeDepenseId,Collection<PieceJustificative> pieceJustificatives,Map<String, Object> parametres) throws ServiceException {
		//Dossier dossierEnCoursSaisie = dossierDao.readSaisieByPersonneByNatureDeplacement((AgentEtat) userSessionManager.getUser(), natureDaplacement);
		
		//if(Code.NATURE_DEPLACEMENT_TRANSIT_BAGAGGES.equals(dossier.getDeplacement().getNature().getCode()))
		
		if(pieceJustificatives==null){
			//quelles sont les pieces a fournir
			//System.out.println(dossier.getDeplacement().getNature().getCode()+" "+typeDepenseId);
			Collection<PieceJustificativeAFournir> pieceJustificativeAFournirs = pieceJustificativeAFournirDao.readBaseByNatureDeplacementIdByTypeDepenseId(dossier.getDeplacement().getNature().getCode(),
					typeDepenseId);
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
			if(Boolean.FALSE.equals(pieceJustificative.getModel().getConditionnee()) )
				newPieceJustificatives.add(pieceJustificative);
		
		PieceJustificativeAFournir modelPiece = null;
		if(parametres!=null){
			PieceJustificative pieceJustificativeExistante = null;
			//Extrait de mariage
			modelPiece = pieceJustificativeAFournirDao.readByNatureDeplacementIdByTypePieceIdByTypeDepenseId(dossier.getDeplacement().getNature().getCode(), Code.TYPE_PIECE_EXTRAIT_MARIAGE,typeDepenseId);
			if(Boolean.TRUE.equals(parametres.get(constantResources.getFormParamMarie()))){
				for(PieceJustificative pj : pieceJustificatives)
					if(pj.getModel().equals(modelPiece)){
						pieceJustificativeExistante = pj;
						break;
					}
				if(pieceJustificativeExistante==null)
					newPieceJustificatives.add(new PieceJustificative(modelPiece));
				else
					newPieceJustificatives.add(pieceJustificativeExistante);
			}else{
				
			}
			
			//Extrait de naissances des enfants
			modelPiece = pieceJustificativeAFournirDao.readByNatureDeplacementIdByTypePieceIdByTypeDepenseId(dossier.getDeplacement().getNature().getCode(), Code.TYPE_PIECE_EXTRAIT_NAISSANCE,typeDepenseId);
			Integer ne = (Integer) parametres.get(constantResources.getFormParamNombreEnfant());
			if(ne!=null && ne>0){
				for(PieceJustificative pj : pieceJustificatives)
					if(pj.getModel().equals(modelPiece)){
						newPieceJustificatives.add(pj);
						if(--ne==0)
							break;
					}
				if(ne>0)
					for(int i=0;i<ne;i++)
						newPieceJustificatives.add(new PieceJustificative(modelPiece));
			}
			
		}
		
		return newPieceJustificatives;
	}
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
	
	@Override
	public Map<String, Object> findParametresByDossierByTypeDepenseId(Dossier dossier,String typeDepenseId,Collection<PieceJustificative> pieceJustificatives)throws ServiceException {
		Map<String, Object> parametres = new HashMap<String, Object>();
		PieceJustificativeAFournir modelPiece = null;
		boolean dossierPersiste = dossierDao.exist(dossier.getNumero());
		//System.out.println(dossierPersiste);
		//Extrait de mariage
		parametres.put(constantResources.getFormParamMarie(), dossierPersiste?false:null);
		modelPiece = pieceJustificativeAFournirDao.readByNatureDeplacementIdByTypePieceIdByTypeDepenseId(dossier.getDeplacement().getNature().getCode(), Code.TYPE_PIECE_EXTRAIT_MARIAGE,typeDepenseId);
		for(PieceJustificative pj : pieceJustificatives)
			if(pj.getModel().equals(modelPiece)){
				parametres.put(constantResources.getFormParamMarie(), true);
				break;
			}
		//nombre d'enfant
		int ne = 0;
		modelPiece = pieceJustificativeAFournirDao.readByNatureDeplacementIdByTypePieceIdByTypeDepenseId(dossier.getDeplacement().getNature().getCode(), Code.TYPE_PIECE_EXTRAIT_NAISSANCE,typeDepenseId);
		for(PieceJustificative pj : pieceJustificatives)
			if(pj.getModel().equals(modelPiece))
				ne++;
		parametres.put(constantResources.getFormParamNombreEnfant(), ne);
		return parametres;
	}

}
