package ci.gouv.budget.solde.sigdcp.dao.dossier;

import java.util.Collection;

import ci.gouv.budget.solde.sigdcp.dao.DataAccessObject;
import ci.gouv.budget.solde.sigdcp.model.dossier.PieceJustificativeAFournir;

public interface PieceJustificativeAFournirDao extends DataAccessObject<PieceJustificativeAFournir,Long> {

	Collection<PieceJustificativeAFournir> readAllByNatureDeplacementId(String id);
	
	PieceJustificativeAFournir readByNatureDeplacementIdByTypePieceId(String id,String typePieceId);
	
	/**
	 * Ramene les pieces à fournir pas conditionée et dérivée
	 * @param id
	 * @return
	 */
	Collection<PieceJustificativeAFournir> readBaseByNatureDeplacementId(String id);
	
	Collection<PieceJustificativeAFournir> readDeriveeByNatureDeplacementId(String id);

}
