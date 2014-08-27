package ci.gouv.budget.solde.sigdcp.dao.identification;

import java.io.Serializable;

import javax.persistence.NoResultException;

import ci.gouv.budget.solde.sigdcp.dao.JpaDaoImpl;
import ci.gouv.budget.solde.sigdcp.model.identification.AgentEtat;
import ci.gouv.budget.solde.sigdcp.model.identification.DelegueSotra;

public class DelegueSotraDaoImpl extends JpaDaoImpl<DelegueSotra, Long> implements DelegueSotraDao , Serializable {

	private static final long serialVersionUID = -2609724288199083806L;

	@Override
	public DelegueSotra readByAgentEtat(AgentEtat agentEtat) {
		try {
			return entityManager.createQuery("SELECT delegue FROM DelegueSotra delegue WHERE delegue.agent = :agentEtat ", clazz)
					.setParameter("agentEtat", agentEtat)
					.getSingleResult();
		} catch (NoResultException e) { 
			return null;
		}
	}

	@Override
	public DelegueSotra readByBeneficiaire(AgentEtat agentEtat) {
		try {
			return entityManager.createQuery("SELECT delegue FROM DelegueSotra delegue WHERE EXISTS ( "
					+ " SELECT beneficiaire FROM BeneficiaireCarteSotra beneficiaire WHERE beneficiaire.agent = :agentEtat AND beneficiaire.delegue = delegue) ", clazz)
					.setParameter("agentEtat", agentEtat)
					.getSingleResult();
		} catch (NoResultException e) { 
			return null;
		}
	}

}  