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
	public PieceJustificativeAFournir readByNatureDeplacementIdByTypePieceIdByTypeDepenseId(String id, String typePieceId,String typeDepenseId) {
		try {
			return entityManager.createQuery("SELECT pj FROM PieceJustificativeAFournir pj WHERE pj.natureDeplacement.code = :ndId AND pj.typePieceJustificative.code = :typePieceId AND "
					+ "pj.typeDepense.code = :tdc", clazz)
					.setParameter("ndId", id)
					.setParameter("typePieceId", typePieceId)
					.setParameter("tdc", typeDepenseId)
					.getSingleResult();
		} catch (NoResultException e) {
			return null;
		} catch (NonUniqueResultException e) {
			throw e;
		}
	}
	
	@Override
	public Collection<PieceJustificativeAFournir> readBaseByNatureDeplacementIdByTypeDepenseId(String natureDeplacementId,String typeDepenseId) {
		return entityManager.createQuery("SELECT pj FROM PieceJustificativeAFournir pj WHERE pj.natureDeplacement.code = :ndId AND pj.typeDepense.code =:tdId AND "
				+ " NOT pj.conditionnee AND NOT pj.derivee", clazz)
				.setParameter("ndId", natureDeplacementId)
				.setParameter("tdId", typeDepenseId)
				.getResultList();
	}
	
	@Override
	public Collection<PieceJustificativeAFournir> readDeriveeByNatureDeplacementIdByTypeDepenseId(String natureDeplacementId,String typeDepenseId) {
		return entityManager.createQuery("SELECT pj FROM PieceJustificativeAFournir pj WHERE pj.natureDeplacement.code = :ndId AND pj.typeDepense.code =:tdId "
				+ " AND pj.derivee=true", clazz)
				.setParameter("ndId", natureDeplacementId)
				.setParameter("tdId", typeDepenseId)
				.getResultList();
	}

}
 