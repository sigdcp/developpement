package ci.gouv.budget.solde.sigdcp.service.identification;

import ci.gouv.budget.solde.sigdcp.model.identification.AgentMissionReference;
import ci.gouv.budget.solde.sigdcp.service.AbstractService;

public interface AgentMissionReferenceService extends AbstractService<AgentMissionReference, Long> {
	
	public void enregistrer(AgentMissionReference agentMissionReference);
	 
}
