package ci.gouv.budget.solde.sigdcp.dao.dossier;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.NoResultException;

import ci.gouv.budget.solde.sigdcp.model.dossier.Deplacement;
import ci.gouv.budget.solde.sigdcp.model.dossier.Dossier;
import ci.gouv.budget.solde.sigdcp.model.dossier.PieceJustificative;

public class PieceJustificativeDaoImpl extends AbstractDocumentDaoImpl<PieceJustificative> implements PieceJustificativeDao, Serializable {

	private static final long serialVersionUID = -2609724288199083806L;

	
	@Override
	public Collection<PieceJustificative> readByDossier(Dossier dossier) {
		return entityManager.createQuery("SELECT pj FROM PieceJustificative pj WHERE pj.dossier=:dossier", clazz)
				.setParameter("dossier", dossier)
				.getResultList();
	}
	
	@Override
	public PieceJustificative readAdministrativeByDossier(Dossier dossier) {
		try {
			return entityManager.createQuery("SELECT pj FROM PieceJustificative pj WHERE pj.dossier=:dossier AND pj.model.config.administrative=1", clazz)
					.setParameter("dossier", dossier)
					.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
	
	@Override
	public Collection<PieceJustificative> readByDossierByTypeId(Dossier dossier,String typeId) {
		return entityManager.createQuery("SELECT pj FROM PieceJustificative pj WHERE pj.model.typePieceJustificative.code = :tpjId AND pj.dossier=:dossier", clazz)
				.setParameter("dossier", dossier).setParameter("tpjId", typeId)
				.getResultList();
	}
	
	@Override
	public Long countByDossierByTypePieceJustificativeId(Dossier dossier, String typeId) {
		return entityManager.createQuery("SELECT COUNT(pj) FROM PieceJustificative pj WHERE pj.model.typePieceJustificative.code= :tpj AND pj.dossier=:dossier", Long.class)
				.setParameter("dossier", dossier).setParameter("tpj", typeId)
				.getSingleResult();
	}
	
	@Override
	public Collection<PieceJustificative> readByDeplacementByTypeId(Deplacement deplacement, String typeId) {
		return entityManager.createQuery("SELECT pj FROM PieceJustificative pj WHERE pj.model.typePieceJustificative.code= :tpj AND pj.dossier.deplacement=:deplacement", clazz)
				.setParameter("deplacement", deplacement).setParameter("tpj", typeId)
				.getResultList();
	}

	

}
 