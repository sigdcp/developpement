package ci.gouv.budget.solde.sigdcp.service.indemnite;

import ci.gouv.budget.solde.sigdcp.model.dossier.BulletinLiquidation;
import ci.gouv.budget.solde.sigdcp.service.ServiceException;


public interface IndemniteCalculateurService {

	void calculer(BulletinLiquidation bulletinLiquidation) throws ServiceException;
	
}
