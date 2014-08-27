package ci.gouv.budget.solde.sigdcp.dao.sotra;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import javax.persistence.NoResultException;

import ci.gouv.budget.solde.sigdcp.dao.traitement.TraitableDaoImpl;
import ci.gouv.budget.solde.sigdcp.model.Code;
import ci.gouv.budget.solde.sigdcp.model.identification.AgentEtat;
import ci.gouv.budget.solde.sigdcp.model.sotra.AchatCarteSotra;
import ci.gouv.budget.solde.sigdcp.model.sotra.CarteSotra;

public class CarteSotraDaoImpl extends TraitableDaoImpl<CarteSotra, Long> implements CarteSotraDao, Serializable {

	private static final long serialVersionUID = -2609724288199083806L;

	@Override
	public Collection<CarteSotra> readByAchat(AchatCarteSotra achat) {
		return entityManager.createQuery("SELECT carte FROM CarteSotra carte WHERE carte.achat = :achat "
				+ "AND carte.traitable.dernierTraitement.operation.nature.code <> :dop "
				//+ "AND carte.traitable.dernierTraitement.statut.code <> :dopstat "
				, clazz)
				.setParameter("achat", achat)
				.setParameter("dop", Code.NATURE_OPERATION_ANNULATION_CS)
				//.setParameter("dopstat", Code.STATUT_ACCEPTE)
				.getResultList();
	}

	@Override
	public CarteSotra readByAchatByAgentEtat(AchatCarteSotra achat,AgentEtat agentEtat) {
		try{
			return entityManager.createQuery("SELECT carte FROM CarteSotra carte WHERE carte.beneficiaire.agent = :agentEtat AND carte.achat = :achat ", clazz)
					.setParameter("agentEtat", agentEtat)
					.setParameter("achat", achat)
					.getSingleResult();
		}catch(NoResultException e){
			return null;
		}
	}

	@Override
	public Long countByAchat(AchatCarteSotra achat) {
		return entityManager.createQuery("SELECT COUNT(carte) FROM CarteSotra carte WHERE carte.achat = :achat ", Long.class)
				.setParameter("achat", achat)
				.getSingleResult();
	}

	@Override
	public CarteSotra readDernierByAgent(AgentEtat agentEtat) {
		List<CarteSotra> list = entityManager.createQuery("SELECT carte FROM CarteSotra carte WHERE carte.beneficiaire.agent = :agentEtat ORDER BY carte.traitable.dernierTraitement.operation.date DESC ", clazz)
				.setParameter("agentEtat", agentEtat)
				.setMaxResults(1)
				.getResultList();
		return list.isEmpty()?null:list.get(0);
	}

	@Override
	public Collection<CarteSotra> readByAgent(AgentEtat agent) {
		return entityManager.createQuery("SELECT carte FROM CarteSotra carte WHERE carte.beneficiaire.agent = :agent ", clazz)
				.setParameter("agent", agent)
				.getResultList();
	}


}
