package ci.gouv.budget.solde.sigdcp.service.identification;

import java.io.Serializable;

import javax.inject.Inject;

import ci.gouv.budget.solde.sigdcp.dao.identification.PersonneDao;
import ci.gouv.budget.solde.sigdcp.model.identification.Personne;

public class PersonneServiceImpl extends AbstractPersonneServiceImpl<Personne> implements PersonneService , Serializable {

	private static final long serialVersionUID = -7601857525393731774L;
	
	@Inject
	public PersonneServiceImpl(PersonneDao dao) {
		super(dao);
	}
	

}

