package ci.gouv.budget.solde.sigdcp.dao.identification;

import ci.gouv.budget.solde.sigdcp.model.identification.AgentEtat;

public interface AgentEtatDao extends AbstractPersonneDao<AgentEtat> {

	AgentEtat readByMatricule(String matricule);


}
 