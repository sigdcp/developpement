package ci.gouv.budget.solde.sigdcp.service.dossier;

import ci.gouv.budget.solde.sigdcp.model.dossier.Dossier;
import ci.gouv.budget.solde.sigdcp.model.dossier.Statut;
import ci.gouv.budget.solde.sigdcp.service.AbstractService;

public interface StatutService extends AbstractService<Statut,String> {

	Statut findCourantByDossier(Dossier dossier);
	
}
