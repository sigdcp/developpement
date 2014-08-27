package ci.gouv.budget.solde.sigdcp.dao.dossier;

import java.util.Collection;

import ci.gouv.budget.solde.sigdcp.model.dossier.PieceProduite;

public interface PieceProduiteDao extends AbstractPieceProduiteDao<PieceProduite> {

	Collection<PieceProduite> readByTypeId(String typeId);
	
	Collection<PieceProduite> readByCategorieIdByTypePieceId(String cdid,String typeId);
	
}
