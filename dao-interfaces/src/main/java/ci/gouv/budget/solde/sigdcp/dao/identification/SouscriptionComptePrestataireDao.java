package ci.gouv.budget.solde.sigdcp.dao.identification;

import java.util.Collection;

import ci.gouv.budget.solde.sigdcp.model.identification.souscription.SouscriptionComptePrestataire;

public interface SouscriptionComptePrestataireDao extends AbstractSouscriptionDao<SouscriptionComptePrestataire> {

	Collection<SouscriptionComptePrestataire> readDateValidationIsNullByTypePrestataireId(String typePrestataireId);

	//SouscriptionComptePersonne readByMatricule(String matricule);
	
}
