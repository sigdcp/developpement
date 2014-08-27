package ci.gouv.budget.solde.sigdcp.dao.traitement;

import java.io.Serializable;
import java.util.Collection;

import ci.gouv.budget.solde.sigdcp.dao.JpaDaoImpl;
import ci.gouv.budget.solde.sigdcp.model.AbstractModel;

public class TraitableDaoImpl<TRAITABLE extends AbstractModel<TID>, TID> extends JpaDaoImpl<TRAITABLE, TID> implements TraitableDao<TRAITABLE, TID>, Serializable {

	private static final long serialVersionUID = -2609724288199083806L;

	@Override
	public Collection<TRAITABLE> readByNatureOperationIdByStatutId(String natureOperationId, String statutId) {
		return entityManager.createQuery("SELECT record FROM "+clazz.getSimpleName()+" record WHERE record.traitable.dernierTraitement.operation.nature.code = :natureOperationId "
				+ "AND record.traitable.dernierTraitement.statut.code = :statutId", clazz)
				.setParameter("natureOperationId", natureOperationId)
				.setParameter("statutId", statutId)
				.getResultList();
	}

}
