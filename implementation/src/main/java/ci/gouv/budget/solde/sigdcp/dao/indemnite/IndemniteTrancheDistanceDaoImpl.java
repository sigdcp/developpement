package ci.gouv.budget.solde.sigdcp.dao.indemnite;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.NoResultException;

import ci.gouv.budget.solde.sigdcp.dao.JpaDaoImpl;
import ci.gouv.budget.solde.sigdcp.model.dossier.CategorieDeplacement;
import ci.gouv.budget.solde.sigdcp.model.indemnite.IndemniteTrancheDistance;

public class IndemniteTrancheDistanceDaoImpl extends JpaDaoImpl<IndemniteTrancheDistance, Long> implements IndemniteTrancheDistanceDao, Serializable {

	private static final long serialVersionUID = -2609724288199083806L;

	@Override
	public IndemniteTrancheDistance readByValeurByCategorieDeplacement(CategorieDeplacement categorieDeplacement, BigDecimal valeur) {
		try {
			return entityManager.createQuery("SELECT tranche FROM IndemniteTrancheDistance tranche WHERE :valeur BETWEEN tranche.intervalleMin AND tranche.intervalleMax "
					+ "AND tranche.categorieDeplacement = :categorieDeplacement ", clazz)
					.setParameter("valeur", valeur)
					.setParameter("categorieDeplacement", categorieDeplacement)
					.getSingleResult();
		} catch (NoResultException e) {
			return null; 
		}
	}

	

}
