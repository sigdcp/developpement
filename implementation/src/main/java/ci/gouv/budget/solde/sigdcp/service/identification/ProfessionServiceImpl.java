package ci.gouv.budget.solde.sigdcp.service.identification;

import javax.inject.Inject;

import ci.gouv.budget.solde.sigdcp.dao.identification.ProfessionDao;
import ci.gouv.budget.solde.sigdcp.model.identification.Profession;
import ci.gouv.budget.solde.sigdcp.service.DefaultServiceImpl;

public class ProfessionServiceImpl extends DefaultServiceImpl<Profession, String> implements ProfessionService {

	private static final long serialVersionUID = 6955724451409903827L;

	@Inject
	public ProfessionServiceImpl(ProfessionDao dao) {
		super(dao);
	}

}
