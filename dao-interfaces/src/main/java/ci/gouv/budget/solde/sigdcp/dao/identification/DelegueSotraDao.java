package ci.gouv.budget.solde.sigdcp.dao.identification;

import ci.gouv.budget.solde.sigdcp.dao.DataAccessObject;
import ci.gouv.budget.solde.sigdcp.model.identification.AgentEtat;
import ci.gouv.budget.solde.sigdcp.model.identification.DelegueSotra;

public interface DelegueSotraDao extends DataAccessObject<DelegueSotra,Long> {

	DelegueSotra readByAgentEtat(AgentEtat agentEtat);

	DelegueSotra readByBeneficiaire(AgentEtat agentEtat);
}
 