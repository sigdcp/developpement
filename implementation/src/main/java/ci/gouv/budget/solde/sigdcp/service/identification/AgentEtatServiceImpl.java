package ci.gouv.budget.solde.sigdcp.service.identification;

import java.io.Serializable;

import javax.inject.Inject;

import ci.gouv.budget.solde.sigdcp.dao.identification.AgentEtatDao;
import ci.gouv.budget.solde.sigdcp.model.identification.AgentEtat;

public class AgentEtatServiceImpl extends AbstractPersonneServiceImpl<AgentEtat> implements AgentEtatService , Serializable {

	private static final long serialVersionUID = -7601857525393731774L;
	
	@Inject
	public AgentEtatServiceImpl(AgentEtatDao beneficiaireDao) {
		super(beneficiaireDao);
	}
	 
	@Override
	public AgentEtat findByMatricule(String matricule) {
		return ((AgentEtatDao)dao).readByMatricule(matricule);
	}


}

