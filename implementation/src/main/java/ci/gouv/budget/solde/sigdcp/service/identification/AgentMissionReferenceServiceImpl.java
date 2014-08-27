package ci.gouv.budget.solde.sigdcp.service.identification;

import java.io.Serializable;

import javax.ejb.Stateless;
import javax.inject.Inject;

import ci.gouv.budget.solde.sigdcp.dao.identification.AgentMissionReferenceDao;
import ci.gouv.budget.solde.sigdcp.model.identification.AgentMissionReference;
import ci.gouv.budget.solde.sigdcp.service.DefaultServiceImpl;

@Stateless
public class AgentMissionReferenceServiceImpl extends DefaultServiceImpl<AgentMissionReference, Long> implements AgentMissionReferenceService , Serializable {

	private static final long serialVersionUID = -7601857525393731774L;
	
	@Inject AgentMissionReferenceDao agentMissionReferenceDao;
	

	@Inject
	public AgentMissionReferenceServiceImpl(AgentMissionReferenceDao dao) {
		super(dao);
	}

	@Override
	public void enregistrer(AgentMissionReference agentMissionReference) {		
		Long matricule = agentMissionReferenceDao.readLastMatricule();
		matricule+=1;
		agentMissionReference.setMatricule(matricule);
		System.out.println(matricule+" : Existe = "+(agentMissionReferenceDao.read(matricule)!=null));	
			if(agentMissionReferenceDao.read(matricule)==null)
				agentMissionReferenceDao.create(agentMissionReference); 
			else 
				enregistrer(agentMissionReference);
		
	}

	


}
