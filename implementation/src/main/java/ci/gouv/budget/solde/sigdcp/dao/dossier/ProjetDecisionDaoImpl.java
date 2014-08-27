package ci.gouv.budget.solde.sigdcp.dao.dossier;

import java.io.Serializable;
import java.util.Collection;

import ci.gouv.budget.solde.sigdcp.model.dossier.NatureDeplacement;
import ci.gouv.budget.solde.sigdcp.model.dossier.ProjetDecision;

public class ProjetDecisionDaoImpl extends AbstractPieceProduiteDaoImpl<ProjetDecision> implements ProjetDecisionDao, Serializable {

	private static final long serialVersionUID = -2609724288199083806L;

	
	@Override
	public Collection<ProjetDecision> readByNatureDeplacementsByNatureOperationIdByStatutId(Collection<NatureDeplacement> natureDeplacements, String natureOperationId, String statutId) {
		return entityManager.createQuery("SELECT bl FROM ProjetDecision bl WHERE bl.traitable.dernierTraitement.operation.nature.code = :natureOperationId AND bl.traitable.dernierTraitement.statut.code = :statutId "
				+ " AND bl.dossier.deplacement.nature IN :natureDeplacements", clazz)
				.setParameter("natureDeplacements", natureDeplacements)
				.setParameter("natureOperationId", natureOperationId)
				.setParameter("statutId", statutId)
				.getResultList();
	}

	
	
	


}
 