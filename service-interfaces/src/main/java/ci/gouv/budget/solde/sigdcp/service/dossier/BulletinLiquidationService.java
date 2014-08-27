package ci.gouv.budget.solde.sigdcp.service.dossier;

import java.util.Collection;

import ci.gouv.budget.solde.sigdcp.model.dossier.BordereauTransmission;
import ci.gouv.budget.solde.sigdcp.model.dossier.BulletinLiquidation;
import ci.gouv.budget.solde.sigdcp.model.dossier.Dossier;

public interface BulletinLiquidationService extends AbstractPieceProduiteService<BulletinLiquidation> {

	Collection<BulletinLiquidation> findByBordereau(BordereauTransmission bordereauTransmission);
	
	BulletinLiquidation nouveau(Dossier dossier);
	
	BulletinLiquidation findByNumero(String numero,String natureoperationCode);
}
