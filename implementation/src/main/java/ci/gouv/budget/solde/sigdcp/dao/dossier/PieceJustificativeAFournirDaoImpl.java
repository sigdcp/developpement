package ci.gouv.budget.solde.sigdcp.dao.dossier;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;

import ci.gouv.budget.solde.sigdcp.dao.JpaDaoImpl;
import ci.gouv.budget.solde.sigdcp.model.dossier.PieceJustificativeAFournir;
import ci.gouv.budget.solde.sigdcp.model.dossier.PieceJustificativeAFournirConfig;

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
				+ " pj.config.conditionnee=0 AND  pj.config.derivee=0", clazz)
				.setParameter("ndId", natureDeplacementId)
				.setParameter("tdId", typeDepenseId)
				.getResultList();
	}
	
	@Override
	public Collection<PieceJustificativeAFournir> readDeriveeByNatureDeplacementIdByTypeDepenseId(String natureDeplacementId,String typeDepenseId) {
		return entityManager.createQuery("SELECT pj FROM PieceJustificativeAFournir pj WHERE pj.natureDeplacement.code = :ndId AND pj.typeDepense.code =:tdId "
				+ " AND pj.config.derivee=1", clazz)
				.setParameter("ndId", natureDeplacementId)
				.setParameter("tdId", typeDepenseId)
				.getResultList();
	}
	
	@Override
	public PieceJustificativeAFournir readAdministrativeByNatureDeplacementIdByTypeDepenseId(String natureDeplacementId,String typeDepenseId) {
		try {
			return entityManager.createQuery("SELECT pj FROM PieceJustificativeAFournir pj WHERE pj.natureDeplacement.code = :ndId AND pj.typeDepense.code =:tdId "
					+ " AND pj.config.administrative=1", clazz)
					.setParameter("ndId", natureDeplacementId)
					.setParameter("tdId", typeDepenseId)
					.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
	
	@Override
	public Collection<PieceJustificativeAFournir> readByNatureDeplacementIdByTypeDepenseId(String natureDeplacementId, String typeDepenseId, PieceJustificativeAFournirConfig config) {
		return entityManager.createQuery("SELECT pj FROM PieceJustificativeAFournir pj WHERE pj.natureDeplacement.code = :ndId AND pj.typeDepense.code =:tdId "
				+ " AND pj.config.commune= :commune AND pj.config.principale= :principale AND pj.config.derivee= :derivee AND pj.config.conditionnee= :conditionnee", 
				//+ " AND pj.config= :config", 
				clazz)
				.setParameter("ndId", natureDeplacementId).setParameter("tdId", typeDepenseId)
				.setParameter("commune", config.getCommune()).setParameter("principale", config.getPrincipale()).setParameter("derivee", config.getDerivee())
				.setParameter("conditionnee", config.getConditionnee())
				//.setParameter("config", config)
				.getResultList();
	}

}
 