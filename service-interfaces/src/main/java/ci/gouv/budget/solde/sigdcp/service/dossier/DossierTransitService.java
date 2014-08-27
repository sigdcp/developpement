package ci.gouv.budget.solde.sigdcp.service.dossier;

import java.util.Collection;

import ci.gouv.budget.solde.sigdcp.model.dossier.DossierTransit;


public interface DossierTransitService extends AbstractDossierService<DossierTransit> {
	
	Collection<DossierTransit> findEditerAttestationPriseEnCharge();
	 
}
 