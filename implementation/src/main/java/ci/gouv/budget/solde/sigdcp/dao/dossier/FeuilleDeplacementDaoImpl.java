package ci.gouv.budget.solde.sigdcp.dao.dossier;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.NoResultException;

import ci.gouv.budget.solde.sigdcp.model.dossier.Dossier;
import ci.gouv.budget.solde.sigdcp.model.dossier.FeuilleDeplacement;
import ci.gouv.budget.solde.sigdcp.model.dossier.NatureDeplacement;

public class FeuilleDeplacementDaoImpl extends AbstractPieceProduiteDaoImpl<FeuilleDeplacement> implements FeuilleDeplacementDao, Serializable {

	private static final long serialVersionUID = -2609724288199083806L;

	@Override
	public Collection<FeuilleDeplacement> readByDernierTraitementIsNull(Collection<NatureDeplacement> natureDeplacements) {
		return entityManager.createQuery("SELECT fd FROM FeuilleDeplacement fd WHERE fd.dossier.deplacement.nature IN :natureDeplacements AND fd.dernierTraitement IS NULL", clazz)
				.setParameter("natureDeplacements", natureDeplacements)
				.getResultList();
	}


	@Override
	public Collection<FeuilleDeplacement> readByNatureDeplacementsByNatureOperationIdByStatutId(Collection<NatureDeplacement> natureDeplacements, String natureOperationId, String statutId) {
		return entityManager.createQuery("SELECT fd FROM FeuilleDeplacement fd WHERE fd.dernierTraitement.operation.nature.code = :natureOperationId AND fd.dernierTraitement.statut.code = :statutId "
				+ " AND fd.dossier.deplacement.nature IN :natureDeplacements", clazz)
				.setParameter("natureDeplacements", natureDeplacements)
				.setParameter("natureOperationId", natureOperationId)
				.setParameter("statutId", statutId)
				.getResultList();
	}


	@Override
	public FeuilleDeplacement readByDossier(Dossier dossier) {
		try{
		return entityManager.createQuery("SELECT fd FROM FeuilleDeplacement fd WHERE fd.dossier = :dossier", clazz)
				.setParameter("dossier", dossier)
				.getSingleResult();
		}catch(NoResultException e){
			return null;
		}
	}

	


}
 