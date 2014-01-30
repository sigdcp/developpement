package ci.gouv.budget.solde.sigdcp.service.identification;

import java.io.Serializable;
import java.util.Date;

import javax.ejb.Stateless;
import javax.inject.Inject;

import ci.gouv.budget.solde.sigdcp.dao.identification.AgentEtatDao;
import ci.gouv.budget.solde.sigdcp.model.identification.AgentEtat;
import ci.gouv.budget.solde.sigdcp.service.ServiceException;

@Stateless
public class AgentEtatServiceImpl extends AbstractPersonneServiceImpl<AgentEtat> implements AgentEtatService , Serializable {

	private static final long serialVersionUID = -7601857525393731774L;
	
	@Inject
	public AgentEtatServiceImpl(AgentEtatDao beneficiaireDao) {
		super(beneficiaireDao);
	}
	 
	@Override
	public void inscrire(AgentEtat agentEtat) throws ServiceException {
		/*
		 * Préconditions
		 */
		//ce matricule nest pas encore enregistré
		if( ((AgentEtatDao)dao).readByMatricule(agentEtat.getMatricule()) !=null )
			throw new ServiceException("Ce matricule est déja enregistré");
		
		agentEtat.setCode(System.currentTimeMillis()+"");
		agentEtat.setDateCreation(new Date());
		
		try {
			dao.create(agentEtat);
		} catch (Exception e) {
			throw new ServiceException("Erreur lors de l'inscription!");
		}
	}


}

