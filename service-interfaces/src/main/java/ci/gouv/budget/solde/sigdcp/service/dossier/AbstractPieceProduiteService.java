package ci.gouv.budget.solde.sigdcp.service.dossier;

import java.util.Collection;

import ci.gouv.budget.solde.sigdcp.model.dossier.NatureDeplacement;
import ci.gouv.budget.solde.sigdcp.model.dossier.PieceProduite;
import ci.gouv.budget.solde.sigdcp.service.traitement.TraitableService;

public interface AbstractPieceProduiteService<PIECE_PRODUITE extends PieceProduite> extends TraitableService<PIECE_PRODUITE,Long> {

	Collection<PIECE_PRODUITE> findATraiter(Collection<NatureDeplacement> natureDeplacements,String codeNatureOperation);
	
}
