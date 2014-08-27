package ci.gouv.budget.solde.sigdcp.dao.dossier;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;

import ci.gouv.budget.solde.sigdcp.model.dossier.BordereauTransmission;
import ci.gouv.budget.solde.sigdcp.model.dossier.NatureDeplacement;

public class BordereauTransmissionDaoImpl extends AbstractPieceProduiteDaoImpl<BordereauTransmission> implements BordereauTransmissionDao, Serializable {

	private static final long serialVersionUID = -2609724288199083806L;

	@Override
	public Collection<BordereauTransmission> readByDernierTraitementIsNull() {
		return entityManager.createQuery("SELECT bt FROM BordereauTransmission bt WHERE bt.traitable.dernierTraitement IS NULL", clazz)
				.getResultList();
	}

	@Override
	public Collection<BordereauTransmission> readByStatutId(String statutId) {
		return entityManager.createQuery("SELECT bt FROM BordereauTransmission bt WHERE bt.traitable.dernierTraitement.statut.code = :statutId", clazz)
				.setParameter("statutId", statutId)
				.getResultList();
	}
	
	@Override
	public Collection<BordereauTransmission> readByNatureDeplacementsByNatureOperationIdByStatutId(Collection<NatureDeplacement> natureDeplacements,String natureOperationId, String statutId) { 
		return entityManager.createQuery("SELECT bt FROM BordereauTransmission bt WHERE bt.traitable.dernierTraitement.operation.nature.code = :natureOperationId AND bt.traitable.dernierTraitement.statut.code = :statutId "
				+ " AND EXISTS (SELECT bl FROM BulletinLiquidation bl WHERE bl.bordereauTransmission = bt AND EXISTS "
				+ "( SELECT td FROM TraitementDossier td WHERE td.pieceProduite = bl AND td.dossier.deplacement.nature IN :natureDeplacements ) )", clazz)
				.setParameter("natureDeplacements", natureDeplacements)
				.setParameter("natureOperationId", natureOperationId)
				.setParameter("statutId", statutId)
				.getResultList();
	}
	
	@Override
	public BigDecimal readMontantEnCoursById(Long identifier) {
		return entityManager.createQuery("SELECT bt.montant FROM BordereauTransmission bt WHERE bt.id = :identifier", BigDecimal.class)
			.setParameter("identifier", identifier)
			.getSingleResult();
	}
 
}
 