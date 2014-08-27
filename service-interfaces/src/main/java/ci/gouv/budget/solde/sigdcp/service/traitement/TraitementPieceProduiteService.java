package ci.gouv.budget.solde.sigdcp.service.traitement;

import ci.gouv.budget.solde.sigdcp.model.dossier.PieceProduite;
import ci.gouv.budget.solde.sigdcp.model.traitement.Operation;
import ci.gouv.budget.solde.sigdcp.model.traitement.TraitementPieceProduite;

public interface TraitementPieceProduiteService extends AbstractTraitementService<TraitementPieceProduite> {
	
	TraitementPieceProduite creer(Operation operation,PieceProduite pieceProduite,TraitementPieceProduite traitement,String statutId);
	
}
