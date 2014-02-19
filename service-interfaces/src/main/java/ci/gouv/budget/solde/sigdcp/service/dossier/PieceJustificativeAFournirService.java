package ci.gouv.budget.solde.sigdcp.service.dossier;

import java.util.Collection;

import ci.gouv.budget.solde.sigdcp.model.dossier.Dossier;
import ci.gouv.budget.solde.sigdcp.model.dossier.PieceJustificative;
import ci.gouv.budget.solde.sigdcp.model.dossier.PieceJustificativeAFournir;
import ci.gouv.budget.solde.sigdcp.model.dossier.PieceJustificativeAFournirConfig;
import ci.gouv.budget.solde.sigdcp.service.AbstractService;

public interface PieceJustificativeAFournirService extends AbstractService<PieceJustificativeAFournir,Long> {

	Collection<PieceJustificativeAFournir> findByNatureDeplacementIdByTypeDepenseId(String natureDeplacementId,String typeDepenseId);
	
	PieceJustificativeAFournir findByNatureDeplacementIdByTypePieceIdByTypeDepenseId(String natureDeplacementId,String typePieceId,String typeDepenseId);
	
	Collection<PieceJustificativeAFournir> findBaseByNatureDeplacementIdByTypeDepenseId(String natureDeplacementId,String typeDepenseId);
	
	Collection<PieceJustificativeAFournir> findDeriveeByNatureDeplacementIdByTypeDepenseId(String natureDeplacementId,String typeDepenseId);
	
	Collection<PieceJustificativeAFournir> findDeriveeRestantByDossier(Dossier dossier,Collection<PieceJustificative> fournis);
	
	Collection<PieceJustificativeAFournir> findByNatureDeplacementIdByTypeDepenseId(String natureDeplacementId, String typeDepenseId, PieceJustificativeAFournirConfig config);
}
