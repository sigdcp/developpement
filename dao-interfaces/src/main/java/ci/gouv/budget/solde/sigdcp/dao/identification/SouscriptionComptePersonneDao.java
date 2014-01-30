package ci.gouv.budget.solde.sigdcp.dao.identification;

import ci.gouv.budget.solde.sigdcp.model.identification.souscription.SouscriptionComptePersonne;

public interface SouscriptionComptePersonneDao extends AbstractSouscriptionDao<SouscriptionComptePersonne> {

	SouscriptionComptePersonne readByMatricule(String matricule);
	
}
