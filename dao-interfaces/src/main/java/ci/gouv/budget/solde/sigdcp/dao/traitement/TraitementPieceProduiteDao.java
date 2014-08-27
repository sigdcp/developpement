package ci.gouv.budget.solde.sigdcp.dao.traitement;

import java.util.Collection;

import ci.gouv.budget.solde.sigdcp.model.dossier.PieceProduite;
import ci.gouv.budget.solde.sigdcp.model.traitement.TraitementPieceProduite;

public interface TraitementPieceProduiteDao extends AbstractTraitementDao<TraitementPieceProduite> {

	Collection<TraitementPieceProduite> readByPiece(PieceProduite piece);
	
	Collection<TraitementPieceProduite> readByPieceProduiteByNatureOperationIdByStatutId(PieceProduite pieceProduite,String natureOperationId,String statutId);
}
