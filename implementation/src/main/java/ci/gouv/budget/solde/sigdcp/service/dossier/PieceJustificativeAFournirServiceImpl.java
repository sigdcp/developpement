package ci.gouv.budget.solde.sigdcp.service.dossier;

import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import javax.inject.Inject;

import ci.gouv.budget.solde.sigdcp.dao.dossier.PieceJustificativeAFournirDao;
import ci.gouv.budget.solde.sigdcp.model.dossier.Dossier;
import ci.gouv.budget.solde.sigdcp.model.dossier.PieceJustificative;
import ci.gouv.budget.solde.sigdcp.model.dossier.PieceJustificativeAFournir;
import ci.gouv.budget.solde.sigdcp.model.dossier.PieceJustificativeAFournirConfig;
import ci.gouv.budget.solde.sigdcp.service.DefaultServiceImpl;

public class PieceJustificativeAFournirServiceImpl extends DefaultServiceImpl<PieceJustificativeAFournir, Long> implements PieceJustificativeAFournirService , Serializable {

	private static final long serialVersionUID = -7601857525393731774L;
	
	@Inject
	public PieceJustificativeAFournirServiceImpl(PieceJustificativeAFournirDao dao) {
		super(dao);
	}
	
	@Override
	public Collection<PieceJustificativeAFournir> findByNatureDeplacementIdByTypeDepenseId(String natureDeplacemntId,String typeDepenseId) {
		return null;//((PieceJustificativeAFournirDao)dao).readAllByNatureDeplacementIdByTypeDepenseId(natureDeplacemntId,typeDepenseId);
	}
	
	@Override
	public PieceJustificativeAFournir findByNatureDeplacementIdByTypePieceIdByTypeDepenseId(String natureDeplacemntId,String typePieceId,String typeDepenseId) {
		return ((PieceJustificativeAFournirDao)dao).readByNatureDeplacementIdByTypePieceIdByTypeDepenseId(natureDeplacemntId, typePieceId,typeDepenseId);
	}
	
	@Override
	public Collection<PieceJustificativeAFournir> findBaseByNatureDeplacementIdByTypeDepenseId(String natureDeplacemntId,String typeDepenseId) {
		return ((PieceJustificativeAFournirDao)dao).readBaseByNatureDeplacementIdByTypeDepenseId(natureDeplacemntId,typeDepenseId);
	}
	
	@Override
	public Collection<PieceJustificativeAFournir> findDeriveeByNatureDeplacementIdByTypeDepenseId(String natureDeplacemntId,String typeDepenseId) {
		return ((PieceJustificativeAFournirDao)dao).readDeriveeByNatureDeplacementIdByTypeDepenseId(natureDeplacemntId,typeDepenseId);
	}
	
	@Override
	public Collection<PieceJustificativeAFournir> findDeriveeRestantByDossier(Dossier dossier,Collection<PieceJustificative> fournis) {
		List<PieceJustificativeAFournir> _aFournir = new LinkedList<>(((PieceJustificativeAFournirDao)dao).readDeriveeByNatureDeplacementIdByTypeDepenseId(dossier.getDeplacement().getNature().getCode(),
				dossier.getDeplacement().getTypeDepense().getCode()));
		for(int i=0;i<_aFournir.size();){
			boolean trouve =  false;
			for(PieceJustificative pj : fournis)
				if(pj.getModel().equals(_aFournir.get(i))){
					_aFournir.remove(i);
					trouve=true;
					break;
				}
			if(!trouve)
				i++;
		}
		return _aFournir;
	}
	
	@Override
	public Collection<PieceJustificativeAFournir> findByNatureDeplacementIdByTypeDepenseId(String natureDeplacementId, String typeDepenseId, PieceJustificativeAFournirConfig config) {
		return ((PieceJustificativeAFournirDao)dao).readByNatureDeplacementIdByTypeDepenseId(natureDeplacementId,typeDepenseId,config);
	}

}
