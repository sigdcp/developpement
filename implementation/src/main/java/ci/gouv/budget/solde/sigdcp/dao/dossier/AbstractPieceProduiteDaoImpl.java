package ci.gouv.budget.solde.sigdcp.dao.dossier;

import java.io.Serializable;

import ci.gouv.budget.solde.sigdcp.dao.traitement.TraitableDaoImpl;
import ci.gouv.budget.solde.sigdcp.model.dossier.PieceProduite;

public abstract class AbstractPieceProduiteDaoImpl<PIECE_PRODUITE extends PieceProduite> extends TraitableDaoImpl<PIECE_PRODUITE,Long> implements AbstractPieceProduiteDao<PIECE_PRODUITE>, Serializable {

	private static final long serialVersionUID = -2609724288199083806L;

	

	

}
 