package ci.gouv.budget.solde.sigdcp.service.identification;

import java.io.Serializable;

import javax.ejb.Stateless;
import javax.inject.Inject;

import ci.gouv.budget.solde.sigdcp.dao.identification.AgentEtatReferenceDao;
import ci.gouv.budget.solde.sigdcp.model.identification.AgentEtatReference;
import ci.gouv.budget.solde.sigdcp.service.DefaultServiceImpl;

@Stateless
public class AgentEtatReferenceServiceImpl extends DefaultServiceImpl<AgentEtatReference, String> implements AgentEtatReferenceService , Serializable {

	private static final long serialVersionUID = -7601857525393731774L;
		

	@Inject
	public AgentEtatReferenceServiceImpl(AgentEtatReferenceDao dao) {
		super(dao);
	}
	
 

}
