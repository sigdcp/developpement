package ci.gouv.budget.solde.sigdcp.dao.dossier;

import java.util.Collection;

import ci.gouv.budget.solde.sigdcp.dao.DataAccessObject;
import ci.gouv.budget.solde.sigdcp.model.dossier.GroupeTypePiece;
import ci.gouv.budget.solde.sigdcp.model.dossier.TypePiece;

public interface TypePieceDao extends DataAccessObject<TypePiece,String> {

	
	Collection<TypePiece> readByGroupe(GroupeTypePiece groupe);
	
	Collection<TypePiece> readByGroupeId(String code);

}
