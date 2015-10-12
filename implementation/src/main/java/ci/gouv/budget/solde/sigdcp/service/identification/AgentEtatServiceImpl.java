package ci.gouv.budget.solde.sigdcp.service.identification;

import java.io.Serializable;

import javax.inject.Inject;

import ci.gouv.budget.solde.sigdcp.dao.identification.AgentEtatDao;
import ci.gouv.budget.solde.sigdcp.dao.identification.AgentEtatReferenceDao;
import ci.gouv.budget.solde.sigdcp.model.identification.AgentEtat;
import ci.gouv.budget.solde.sigdcp.model.identification.AgentEtatReference;

public class AgentEtatServiceImpl extends AbstractPersonneServiceImpl<AgentEtat> implements AgentEtatService , Serializable {

	private static final long serialVersionUID = -7601857525393731774L;
	@Inject private AgentEtatReferenceDao agentEtatReferenceDao;
	
	@Inject
	public AgentEtatServiceImpl(AgentEtatDao beneficiaireDao) {
		super(beneficiaireDao);
	}
	 
	@Override
	public AgentEtat findByMatricule(String matricule) {
		return ((AgentEtatDao)dao).readByMatricule(matricule);
	}

	@Override
	public AgentEtat findByMatriculeByReference(String matricule) {
			AgentEtat agentEtat=null;
			agentEtat=findByMatricule(matricule);				
			if(agentEtat==null){ 
				agentEtat  = new AgentEtat();
				AgentEtatReference agentEtatReference=agentEtatReferenceDao.read(matricule);
				if(agentEtatReference!=null){
					agentEtat.setMatricule(agentEtatReference.getMatricule());
					agentEtat.setDateNaissance(agentEtatReference.getDateNaissance());
				}				
			}					
		return agentEtat;
	}


}

