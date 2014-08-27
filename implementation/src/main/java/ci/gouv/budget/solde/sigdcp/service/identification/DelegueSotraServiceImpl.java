package ci.gouv.budget.solde.sigdcp.service.identification;

import java.io.Serializable;

import javax.inject.Inject;

import ci.gouv.budget.solde.sigdcp.dao.identification.DelegueSotraDao;
import ci.gouv.budget.solde.sigdcp.model.identification.AgentEtat;
import ci.gouv.budget.solde.sigdcp.model.identification.DelegueSotra;
import ci.gouv.budget.solde.sigdcp.service.DefaultServiceImpl;

public class DelegueSotraServiceImpl extends DefaultServiceImpl<DelegueSotra, Long> implements DelegueSotraService , Serializable {

	private static final long serialVersionUID = -7601857525393731774L;
	
	@Inject
	public DelegueSotraServiceImpl(DelegueSotraDao dao) {
		super(dao);
	}

	@Override
	public DelegueSotra findByAgentEtat(AgentEtat agentEtat) {
		return ((DelegueSotraDao)dao).readByAgentEtat(agentEtat);
	}

}
