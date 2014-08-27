package ci.gouv.budget.solde.sigdcp.service.identification;

import java.util.Collection;

import ci.gouv.budget.solde.sigdcp.model.identification.souscription.SouscriptionComptePrestataire;

public interface SouscriptionComptePrestataireService extends AbstractSouscriptionService<SouscriptionComptePrestataire> {

	Collection<SouscriptionComptePrestataire> findSouscriptionsAValiderByTypePrestataireId(String typePrestataireId);
 
}
