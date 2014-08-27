package ci.gouv.budget.solde.sigdcp.service.identification;

import ci.gouv.budget.solde.sigdcp.model.identification.AgentEtat;
import ci.gouv.budget.solde.sigdcp.model.identification.DelegueSotra;
import ci.gouv.budget.solde.sigdcp.service.AbstractService;

public interface DelegueSotraService extends AbstractService<DelegueSotra,Long> {

	DelegueSotra findByAgentEtat(AgentEtat agentEtat);
	
}
