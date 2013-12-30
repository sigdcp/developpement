package ci.gouv.budget.solde.sigdcp.service.identification;

import java.io.Serializable;
import java.util.Date;

import javax.ejb.Stateless;
import javax.inject.Inject;

import ci.gouv.budget.solde.sigdcp.dao.identification.PersonneDao;
import ci.gouv.budget.solde.sigdcp.model.identification.Personne;
import ci.gouv.budget.solde.sigdcp.service.ServiceException;

@Stateless
public class PersonneServiceImpl extends AbstractPersonneServiceImpl<Personne> implements PersonneService , Serializable {

	private static final long serialVersionUID = -7601857525393731774L;
	
	@Inject
	public PersonneServiceImpl(PersonneDao ayantDroitDao) {
		super(ayantDroitDao);
	}
	
	@Override
	public void inscrire(Personne personne) throws ServiceException {
		/*
		 * Préconditions
		 */
		
		personne.setCode(System.currentTimeMillis()+"");
		personne.setDateCreation(new Date());
		
		try {
			dao.create(personne);
		} catch (Exception e) {
			throw new ServiceException("Erreur lors de l'inscription!");
		}
	}
	

}

