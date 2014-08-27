package ci.gouv.budget.solde.sigdcp.dao.traitement;

import java.io.Serializable;
import java.util.Collection;

import ci.gouv.budget.solde.sigdcp.model.sotra.AchatCarteSotra;
import ci.gouv.budget.solde.sigdcp.model.traitement.TraitementAchatCarteSotra;

public class TraitementAchatCarteSotraDaoImpl extends AbstractTraitementDaoImpl<TraitementAchatCarteSotra> implements TraitementAchatCarteSotraDao, Serializable {

	private static final long serialVersionUID = -2609724288199083806L;

	@Override
	public Collection<TraitementAchatCarteSotra> readByAchatByNatureOperationIdByStatutId(AchatCarteSotra achat, String natureOperationId, String statutId) {
		return entityManager.createQuery("SELECT traitement FROM TraitementAchatCarteSotra traitement WHERE traitement.achat = :achat AND traitement.operation.nature.code = :noid AND "
				+ "traitement.statut.code = :statutId", clazz)
				.setParameter("achat", achat)
				.setParameter("noid", natureOperationId)
				.setParameter("statutId", statutId)
				.getResultList();
	}


}
