package ci.gouv.budget.solde.sigdcp.service.identification;

import java.io.Serializable;

import ci.gouv.budget.solde.sigdcp.dao.identification.AbstractPersonneDao;
import ci.gouv.budget.solde.sigdcp.model.identification.Personne;
import ci.gouv.budget.solde.sigdcp.service.DefaultServiceImpl;

public abstract class AbstractPersonneServiceImpl<PERSONNE extends Personne> extends DefaultServiceImpl<PERSONNE, Long> implements AbstractPersonneService<PERSONNE> , Serializable {

	private static final long serialVersionUID = -7601857525393731774L;
	
	public AbstractPersonneServiceImpl(AbstractPersonneDao<PERSONNE> personneDao) {
		super(personneDao);
	}
	
	/*
	@Override
	public void validerInscription(PERSONNE personne) throws ServiceException {
		personne.setValide(Boolean.TRUE);
		dao.update(personne);
	}
	
	@Override
	public void validerInscription(Collection<PERSONNE> personnes)throws ServiceException {
		for(PERSONNE personne : personnes)
			validerInscription(personne);
	}
	
	@Override
	public Collection<PERSONNE> getInscriptions() {
		return ((AbstractPersonneDao<PERSONNE>)dao).findByValide(Boolean.FALSE);
	}
	*/

}

