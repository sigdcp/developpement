package ci.gouv.budget.solde.sigdcp.dao.identification;

import java.io.Serializable;

import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;

import ci.gouv.budget.solde.sigdcp.model.identification.AgentEtat;

public class AgentEtatDaoImpl extends AbstractPersonneDaoImpl<AgentEtat> implements AgentEtatDao , Serializable {

	private static final long serialVersionUID = -2609724288199083806L;

	@Override
	public AgentEtat readByMatricule(String matricule) {
		try {
			return entityManager.createQuery("SELECT ae FROM AgentEtat ae WHERE ae.matricule = :matricule", clazz)
					.setParameter("matricule", matricule)
					.getSingleResult();
		} catch (NoResultException e) {
			return null;
		} catch (NonUniqueResultException e) {
			throw e;
		} 
	}
	
}

