package ci.gouv.budget.solde.sigdcp.service.dossier;

import java.util.Collection;

import ci.gouv.budget.solde.sigdcp.model.dossier.PieceJustificativeAFournir;
import ci.gouv.budget.solde.sigdcp.service.AbstractService;

public interface PieceJustificativeAFournirService extends AbstractService<PieceJustificativeAFournir,Long> {

	Collection<PieceJustificativeAFournir> findByNatureDeplacementId(String id);
	
	PieceJustificativeAFournir findByNatureDeplacementIdByTypePiece(String id,String typePieceId);
	
	Collection<PieceJustificativeAFournir> findBaseByNatureDeplacementId(String id);
	
	Collection<PieceJustificativeAFournir> findDeriveeByNatureDeplacementId(String id);
}
