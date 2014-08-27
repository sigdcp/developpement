package ci.gouv.budget.solde.sigdcp.dao.dossier;

import ci.gouv.budget.solde.sigdcp.model.dossier.DossierTransit;
import ci.gouv.budget.solde.sigdcp.model.dossier.PieceProduite;

public interface DossierTransitDao extends AbstractDossierDao<DossierTransit> {

	DossierTransit readByAttestationPriseEnCharge(PieceProduite piece);
	
}
  