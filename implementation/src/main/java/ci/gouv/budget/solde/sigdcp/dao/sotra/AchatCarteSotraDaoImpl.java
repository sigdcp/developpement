package ci.gouv.budget.solde.sigdcp.dao.sotra;

import java.io.Serializable;
import java.util.Collection;

import ci.gouv.budget.solde.sigdcp.dao.traitement.TraitableDaoImpl;
import ci.gouv.budget.solde.sigdcp.model.identification.AgentEtat;
import ci.gouv.budget.solde.sigdcp.model.sotra.AchatCarteSotra;

public class AchatCarteSotraDaoImpl extends TraitableDaoImpl<AchatCarteSotra, Long> implements AchatCarteSotraDao, Serializable {

	private static final long serialVersionUID = -2609724288199083806L;

	@Override
	public Collection<AchatCarteSotra> readByDelegueByNatureOperationIdByStatutId(AgentEtat agent, String natureOperationId, String statutId) {
		return entityManager.createQuery("SELECT achat FROM AchatCarteSotra achat WHERE achat.delegue.agent = :agent AND achat.traitable.dernierTraitement.operation.nature.code = :natureOperationId "
				+ "AND achat.traitable.dernierTraitement.statut.code = :statutId", clazz)
				.setParameter("agent", agent)
				.setParameter("natureOperationId", natureOperationId)
				.setParameter("statutId", statutId)
				.getResultList();
	}
	
	@Override
	public AchatCarteSotra readRecentByDelegue(AgentEtat agent) {
		Collection<AchatCarteSotra> list = entityManager.createQuery("SELECT achat FROM AchatCarteSotra achat WHERE achat.delegue.agent = :agent "
				+ "ORDER BY achat.traitable.dernierTraitement.operation.date DESC", clazz)
				.setParameter("agent", agent)
				.setMaxResults(1)
				.getResultList();
		return list.isEmpty()?null:list.iterator().next();
	}

	@Override
	public Collection<AchatCarteSotra> readCommandeIsNullByNatureOperationIdByStatutId(String natureOperationId, String statutId) {
		return entityManager.createQuery("SELECT achat FROM AchatCarteSotra achat "
				+ "WHERE achat.traitable.dernierTraitement.operation.nature.code = :natureOperationId "
				+ "AND achat.traitable.dernierTraitement.statut.code = :statutId AND NOT EXISTS (SELECT bc FROM CommandeCarteSotra bc WHERE bc.achat = achat) "
				, clazz)
				.setParameter("natureOperationId", natureOperationId)
				.setParameter("statutId", statutId)
				.getResultList();
	}


}
