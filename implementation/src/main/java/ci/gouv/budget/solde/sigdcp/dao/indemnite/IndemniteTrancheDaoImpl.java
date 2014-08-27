package ci.gouv.budget.solde.sigdcp.dao.indemnite;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.NoResultException;

import ci.gouv.budget.solde.sigdcp.dao.JpaDaoImpl;
import ci.gouv.budget.solde.sigdcp.model.indemnite.IndemniteTranche;
import ci.gouv.budget.solde.sigdcp.model.indemnite.TypeIndemniteTranche;

public class IndemniteTrancheDaoImpl extends JpaDaoImpl<IndemniteTranche, Long> implements IndemniteTrancheDao, Serializable {

	private static final long serialVersionUID = -2609724288199083806L;

	@Override
	public IndemniteTranche readByValeurByType(BigDecimal valeur, TypeIndemniteTranche type) {
		try {
			return entityManager.createQuery("SELECT tranche FROM IndemniteTranche tranche WHERE :valeur BETWEEN tranche.intervalleMin AND tranche.intervalleMax AND tranche.type = :typeCode ", clazz)
					.setParameter("valeur", valeur)
					.setParameter("typeCode", type)
					.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	

}
