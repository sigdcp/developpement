package ci.gouv.budget.solde.sigdcp.dao.traitement;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.NoResultException;

import ci.gouv.budget.solde.sigdcp.dao.JpaDaoImpl;
import ci.gouv.budget.solde.sigdcp.dao.traitement.OperationValidationConfigDao;
import ci.gouv.budget.solde.sigdcp.model.dossier.ValidationType;
import ci.gouv.budget.solde.sigdcp.model.traitement.OperationValidationConfig;

public class OperationValidationConfigDaoImpl extends JpaDaoImpl<OperationValidationConfig, Long> implements OperationValidationConfigDao, Serializable {

	private static final long serialVersionUID = -2609724288199083806L;
	
	@Override
	public Collection<OperationValidationConfig> readByNatureOperationId(String natureOperationId) {
		return entityManager.createQuery("SELECT cf FROM OperationValidationConfig cf WHERE cf.natureOperation.code = :natureOperationId", clazz)
			.setParameter("natureOperationId", natureOperationId)
			.getResultList();
	}
	
	@Override
	public OperationValidationConfig readByNatureOperationIdByValidationType(String natureOperationId, ValidationType validationType) {
		
		try {
			return entityManager.createQuery("SELECT cf FROM OperationValidationConfig cf WHERE cf.natureOperation.code = :natureOperationId AND cf.validationType = :validationType", clazz)
				.setParameter("natureOperationId", natureOperationId)
				.setParameter("validationType", validationType)
				.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	@Override
	public Long countByNatureOperationId(String natureOperationId) {
		return entityManager.createQuery("SELECT COUNT(cf) FROM OperationValidationConfig cf WHERE cf.natureOperation.code = :natureOperationId", Long.class)
				.setParameter("natureOperationId", natureOperationId)
				.getSingleResult();
	}
	
	

}
