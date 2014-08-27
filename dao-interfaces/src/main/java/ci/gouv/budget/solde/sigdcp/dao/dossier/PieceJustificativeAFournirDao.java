package ci.gouv.budget.solde.sigdcp.dao.dossier;

import java.util.Collection;

import ci.gouv.budget.solde.sigdcp.dao.DataAccessObject;
import ci.gouv.budget.solde.sigdcp.model.dossier.PieceJustificativeAFournir;
import ci.gouv.budget.solde.sigdcp.model.dossier.PieceJustificativeAFournirConfig;

public interface PieceJustificativeAFournirDao extends DataAccessObject<PieceJustificativeAFournir,Long> {

	Collection<PieceJustificativeAFournir> readAllByNatureDeplacementId(String id);
	
	PieceJustificativeAFournir readByNatureDeplacementIdByTypePieceIdByTypeDepenseId(String natureDeplacementId,String typePieceId,String typeDepenseId);
	
	/**
	 * Ramene les pieces à fournir pas conditionée et dérivée
	 * @param id
	 * @return
	 */
	Collection<PieceJustificativeAFournir> readBaseByNatureDeplacementIdByTypeDepenseId(String natureDeplacementId,String typeDepenseId);
	
	Collection<PieceJustificativeAFournir> readDeriveeByNatureDeplacementIdByTypeDepenseId(String natureDeplacementId,String typeDepenseId);

	Collection<PieceJustificativeAFournir> readByNatureDeplacementIdByTypeDepenseId(String natureDeplacementId,String typeDepenseId,PieceJustificativeAFournirConfig config);
	
	PieceJustificativeAFournir readAdministrativeByNatureDeplacementIdByTypeDepenseId(String natureDeplacementId,String typeDepenseId);
}
