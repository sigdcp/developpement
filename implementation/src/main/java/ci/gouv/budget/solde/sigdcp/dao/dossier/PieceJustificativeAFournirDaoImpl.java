package ci.gouv.budget.solde.sigdcp.dao.dossier;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;

import ci.gouv.budget.solde.sigdcp.dao.JpaDaoImpl;
import ci.gouv.budget.solde.sigdcp.model.dossier.PieceJustificativeAFournir;

public class PieceJustificativeAFournirDaoImpl extends JpaDaoImpl<PieceJustificativeAFournir, Long> implements PieceJustificativeAFournirDao, Serializable {

	private static final long serialVersionUID = -2609724288199083806L;
	
	@Override
	public Collection<PieceJustificativeAFournir> readAllByNatureDeplacementId(String id) {
		return entityManager.createQuery("SELECT pj FROM PieceJustificativeAFournir pj WHERE pj.natureDeplacement.code = :ndId", clazz)
				.setParameter("ndId", id)
				.getResultList();
	}
	
	@Override
	public PieceJustificativeAFournir readByNatureDeplacementIdByTypePieceId(String id, String typePieceId) {
		try {
			return entityManager.createQuery("SELECT pj FROM PieceJustificativeAFournir pj WHERE pj.natureDeplacement.code = :ndId AND pj.typePieceJustificative.code = :typePieceId", clazz)
					.setParameter("ndId", id)
					.setParameter("typePieceId", typePieceId)
					.getSingleResult();
		} catch (NoResultException e) {
			return null;
		} catch (NonUniqueResultException e) {
			throw e;
		}
	}
	
	@Override
	public Collection<PieceJustificativeAFournir> readBaseByNatureDeplacementId(
			String id) {
		return entityManager.createQuery("SELECT pj FROM PieceJustificativeAFournir pj WHERE pj.natureDeplacement.code = :ndId AND "
				+ " NOT pj.conditionnee AND NOT pj.derivee", clazz)
				.setParameter("ndId", id)
				.getResultList();
	}
	
	@Override
	public Collection<PieceJustificativeAFournir> readDeriveeByNatureDeplacementId(String id) {
		return entityManager.createQuery("SELECT pj FROM PieceJustificativeAFournir pj WHERE pj.natureDeplacement.code = :ndId "
				+ " AND pj.derivee=true", clazz)
				.setParameter("ndId", id)
				.getResultList();
	}

}
 