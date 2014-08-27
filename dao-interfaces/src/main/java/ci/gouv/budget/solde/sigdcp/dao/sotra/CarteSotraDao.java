package ci.gouv.budget.solde.sigdcp.dao.sotra;

import java.util.Collection;

import ci.gouv.budget.solde.sigdcp.dao.traitement.TraitableDao;
import ci.gouv.budget.solde.sigdcp.model.identification.AgentEtat;
import ci.gouv.budget.solde.sigdcp.model.sotra.AchatCarteSotra;
import ci.gouv.budget.solde.sigdcp.model.sotra.CarteSotra;

public interface CarteSotraDao extends TraitableDao<CarteSotra,Long> {

	Collection<CarteSotra> readByAchat(AchatCarteSotra achat);
	
	Long countByAchat(AchatCarteSotra achat);
	
	CarteSotra readByAchatByAgentEtat(AchatCarteSotra achat,AgentEtat agentEtat);
	
	CarteSotra readDernierByAgent(AgentEtat agentEtat);

	Collection<CarteSotra> readByAgent(AgentEtat agent);
} 
