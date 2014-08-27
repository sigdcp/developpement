package ci.gouv.budget.solde.sigdcp.dao.dossier;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.NoResultException;

import ci.gouv.budget.solde.sigdcp.model.Code;
import ci.gouv.budget.solde.sigdcp.model.dossier.BordereauTransmission;
import ci.gouv.budget.solde.sigdcp.model.dossier.BulletinLiquidation;
import ci.gouv.budget.solde.sigdcp.model.dossier.NatureDeplacement;

public class BulletinLiquidationDaoImpl extends AbstractPieceProduiteDaoImpl<BulletinLiquidation> implements BulletinLiquidationDao, Serializable {

	private static final long serialVersionUID = -2609724288199083806L;

	@Override
	public Collection<BulletinLiquidation> readByDernierTraitementIsNull(Collection<NatureDeplacement> natureDeplacements) {
		return entityManager.createQuery("SELECT bl FROM BulletinLiquidation bl WHERE bl.dossier.deplacement.nature IN :natureDeplacements AND bl.traitable.dernierTraitement IS NULL", clazz)
				.setParameter("natureDeplacements", natureDeplacements)
				.getResultList();
	}
	
	@Override
	public Collection<BulletinLiquidation> readByBordereau(BordereauTransmission bordereauTransmission) {
		return entityManager.createQuery("SELECT bl FROM BulletinLiquidation bl WHERE bl.bordereauTransmission = :bordereauTransmission", clazz)
				.setParameter("bordereauTransmission", bordereauTransmission)
				.getResultList();
	}

	@Override
	public BulletinLiquidation readByNumero(String numero) {
		try {
			return entityManager.createQuery("SELECT bl FROM BulletinLiquidation bl WHERE bl.numero = :numero", clazz)
					.setParameter("numero", numero)
					.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	@Override
	public Collection<BulletinLiquidation> readByNatureDeplacementsByNatureOperationIdByStatutId(Collection<NatureDeplacement> natureDeplacements, String natureOperationId, String statutId) {
		return entityManager.createQuery("SELECT bl FROM BulletinLiquidation bl WHERE bl.traitable.dernierTraitement.operation.nature.code = :natureOperationId AND bl.traitable.dernierTraitement.statut.code = :statutId "
				+ " AND bl.dossier.deplacement.nature IN :natureDeplacements", clazz)
				.setParameter("natureDeplacements", natureDeplacements)
				.setParameter("natureOperationId", natureOperationId)
				.setParameter("statutId", statutId)
				.getResultList();
	}

	
	@Override
	public Collection<BulletinLiquidation> readByNatureDeplacementsByBordereauNatureOperationIdByBordereauStatutId(Collection<NatureDeplacement> natureDeplacements,String natureOperationId, String statutId) {
		return entityManager.createQuery(
				"SELECT bl FROM BulletinLiquidation bl WHERE bl.dossier.deplacement.nature IN :natureDeplacements AND bl.traitable.dernierTraitement.operation.nature.code <> :reglerOpId AND "
				+ "bl.bordereauTransmission.traitable.dernierTraitement.operation.nature.code = :natureOperationId AND bl.bordereauTransmission.traitable.dernierTraitement.statut.code = :statutId"
				, clazz)
				.setParameter("natureDeplacements", natureDeplacements)
				.setParameter("natureOperationId", natureOperationId)
				.setParameter("statutId", statutId)
				.setParameter("reglerOpId", Code.NATURE_OPERATION_REGLER_BL)
				.getResultList();
	}

	@Override
	public Boolean existByBulletinLiquidationIdNotEqual(BulletinLiquidation bulletinLiquidation) {
		return entityManager.createQuery(
				"SELECT COUNT(bl) FROM BulletinLiquidation bl WHERE bl.dossier = :dossier AND bl <> :blp"
				, Long.class)
				.setParameter("dossier", bulletinLiquidation.getDossier())
				.setParameter("blp", bulletinLiquidation)
				.getSingleResult() > 0;
	}
	


}
 