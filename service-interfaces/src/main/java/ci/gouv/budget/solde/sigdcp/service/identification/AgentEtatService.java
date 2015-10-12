package ci.gouv.budget.solde.sigdcp.service.identification;

import ci.gouv.budget.solde.sigdcp.model.identification.AgentEtat;

public interface AgentEtatService extends AbstractPersonneService<AgentEtat> {

	AgentEtat findByMatricule(String matricule);
	AgentEtat findByMatriculeByReference(String matricule);
	
}
