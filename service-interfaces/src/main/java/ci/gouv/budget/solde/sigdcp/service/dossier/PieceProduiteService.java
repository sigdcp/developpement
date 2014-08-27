package ci.gouv.budget.solde.sigdcp.service.dossier;

import java.util.Collection;

import ci.gouv.budget.solde.sigdcp.model.dossier.PieceProduite;

public interface PieceProduiteService extends AbstractPieceProduiteService<PieceProduite> {
	
	Collection<PieceProduite> findByTypeId(String typeId);

	Collection<PieceProduite> findByCategorieIdByTypePieceId(String cdid,String typeId);
	
}
