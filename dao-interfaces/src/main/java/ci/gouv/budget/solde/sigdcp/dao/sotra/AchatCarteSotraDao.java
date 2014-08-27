package ci.gouv.budget.solde.sigdcp.dao.sotra;

import java.util.Collection;

import ci.gouv.budget.solde.sigdcp.dao.traitement.TraitableDao;
import ci.gouv.budget.solde.sigdcp.model.identification.AgentEtat;
import ci.gouv.budget.solde.sigdcp.model.sotra.AchatCarteSotra;

public interface AchatCarteSotraDao extends TraitableDao<AchatCarteSotra,Long> {
	
	Collection<AchatCarteSotra> readByDelegueByNatureOperationIdByStatutId(AgentEtat agent,String natureOperationId,String statutId);
	
	Collection<AchatCarteSotra> readCommandeIsNullByNatureOperationIdByStatutId(String natureOperationId,String statutId);
	
	AchatCarteSotra readRecentByDelegue(AgentEtat agent);
} 
