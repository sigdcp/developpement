package ci.gouv.budget.solde.sigdcp.dao.dossier;

import java.io.Serializable;
import java.util.Collection;

import ci.gouv.budget.solde.sigdcp.dao.JpaDaoImpl;
import ci.gouv.budget.solde.sigdcp.model.dossier.GroupeTypePiece;
import ci.gouv.budget.solde.sigdcp.model.dossier.TypePiece;

public class TypePieceDaoImpl extends JpaDaoImpl<TypePiece, String> implements TypePieceDao, Serializable {

	private static final long serialVersionUID = -2609724288199083806L;

	@Override
	public Collection<TypePiece> readByGroupe(GroupeTypePiece groupe) {
		return readByGroupeId(groupe.getCode());
	}
	
	@Override
	public Collection<TypePiece> readByGroupeId(String code) {
		return entityManager.createQuery("SELECT tp FROM TypePiece tp WHERE EXISTS("
				+ "SELECT g FROM GroupeTypePiece g WHERE tp MEMBER OF g.typePieces AND g.code=:code"
				+ ")", clazz)
				.setParameter("code", code)
				.getResultList();
	}
	
}
