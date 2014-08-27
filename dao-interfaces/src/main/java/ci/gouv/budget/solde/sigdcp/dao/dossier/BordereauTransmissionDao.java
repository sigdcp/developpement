package ci.gouv.budget.solde.sigdcp.dao.dossier;

import java.math.BigDecimal;
import java.util.Collection;

import ci.gouv.budget.solde.sigdcp.model.dossier.BordereauTransmission;

public interface BordereauTransmissionDao extends AbstractPieceProduiteDao<BordereauTransmission> {

	Collection<BordereauTransmission> readByDernierTraitementIsNull();

	Collection<BordereauTransmission> readByStatutId(String statutId);

	BigDecimal readMontantEnCoursById(Long identifier);
}
 