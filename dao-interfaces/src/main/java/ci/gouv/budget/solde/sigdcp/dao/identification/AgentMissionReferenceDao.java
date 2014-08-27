package ci.gouv.budget.solde.sigdcp.dao.identification;

import ci.gouv.budget.solde.sigdcp.dao.DataAccessObject;
import ci.gouv.budget.solde.sigdcp.model.identification.AgentMissionReference;

public interface AgentMissionReferenceDao extends DataAccessObject<AgentMissionReference,Long> {
	
	public Long readLastMatricule();

	public AgentMissionReference readByEmail(String email);
	
}
