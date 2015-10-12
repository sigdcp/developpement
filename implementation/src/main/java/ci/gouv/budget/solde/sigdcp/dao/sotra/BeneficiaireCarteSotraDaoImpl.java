package ci.gouv.budget.solde.sigdcp.dao.sotra;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.NoResultException;

import ci.gouv.budget.solde.sigdcp.dao.JpaDaoImpl;
import ci.gouv.budget.solde.sigdcp.model.identification.AgentEtat;
import ci.gouv.budget.solde.sigdcp.model.sotra.BeneficiaireCarteSotra;

public class BeneficiaireCarteSotraDaoImpl extends JpaDaoImpl<BeneficiaireCarteSotra, Long> implements BeneficiaireCarteSotraDao, Serializable {

	private static final long serialVersionUID = -2609724288199083806L;

	@Override
	public Collection<BeneficiaireCarteSotra> readByValide(Boolean valide) {
		return entityManager.createQuery("SELECT beneficiaire FROM BeneficiaireCarteSotra beneficiaire WHERE beneficiaire.valide = :valide", clazz)
				.setParameter("valide", valide)
				.getResultList();
	}

	@Override
	public Collection<BeneficiaireCarteSotra> readValideByDelegue(AgentEtat agentEtat) {
		return entityManager.createQuery("SELECT beneficiaire FROM BeneficiaireCarteSotra beneficiaire WHERE beneficiaire.delegue.agent = :agentEtat AND beneficiaire.valide=1", clazz)
				.setParameter("agentEtat", agentEtat)
				.getResultList();
	}

	@Override
	public BeneficiaireCarteSotra readByAgent(AgentEtat agentEtat) {
		try {
			return entityManager.createQuery("SELECT beneficiaire FROM BeneficiaireCarteSotra beneficiaire WHERE beneficiaire.agent = :agentEtat", clazz)
					.setParameter("agentEtat", agentEtat)
					.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	@Override
	public Collection<BeneficiaireCarteSotra> readValideIsNull() {
		return entityManager.createQuery("SELECT beneficiaire FROM BeneficiaireCarteSotra beneficiaire WHERE beneficiaire.valide IS NULL", clazz)
				.getResultList();
	}


}
