package ci.gouv.budget.solde.sigdcp.dao.identification;

import java.io.Serializable;

import javax.persistence.NoResultException;

import ci.gouv.budget.solde.sigdcp.dao.JpaDaoImpl;
import ci.gouv.budget.solde.sigdcp.model.identification.AgentMissionReference;

public class AgentMissionReferenceDaoImpl extends JpaDaoImpl<AgentMissionReference,Long> implements AgentMissionReferenceDao, Serializable {

	private static final long serialVersionUID = -2609724288199083806L;
 
	@Override
	public Long readLastMatricule() {
		try {
		return entityManager.createQuery("SELECT agm FROM AgentMissionReference agm ORDER BY agm.matricule DESC", clazz)
				.setMaxResults(1)
				.getSingleResult().getMatricule();
		} catch (NoResultException e) {
			return 90000l;
		}
	}

	@Override
	public AgentMissionReference readByEmail(String email) {
		try {
			return entityManager.createQuery("SELECT agm FROM AgentMissionReference agm WHERE agm.contact.email = :email", clazz)
					.setParameter("email", email)
					.getSingleResult();
			} catch (NoResultException e) {
				return null;
			}
	}

	
	
}
