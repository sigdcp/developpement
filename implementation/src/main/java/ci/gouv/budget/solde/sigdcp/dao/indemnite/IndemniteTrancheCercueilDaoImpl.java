package ci.gouv.budget.solde.sigdcp.dao.indemnite;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.NoResultException;

import ci.gouv.budget.solde.sigdcp.dao.JpaDaoImpl;
import ci.gouv.budget.solde.sigdcp.model.indemnite.IndemniteTrancheCercueil;

public class IndemniteTrancheCercueilDaoImpl extends JpaDaoImpl<IndemniteTrancheCercueil, Long> implements IndemniteTrancheCercueilDao, Serializable {

	private static final long serialVersionUID = -2609724288199083806L;

	@Override
	public IndemniteTrancheCercueil readByIndice(BigDecimal indice) {
		try {
			return entityManager.createQuery("SELECT tranche FROM IndemniteTrancheCerceuil tranche WHERE :valeur BETWEEN tranche.intervalleMin AND tranche.intervalleMax ", clazz)
					.setParameter("valeur", indice)
					.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	

}
