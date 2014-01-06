package ci.gouv.budget.solde.sigdcp.service.identification;

import javax.ejb.Stateless;
import javax.inject.Inject;

import ci.gouv.budget.solde.sigdcp.dao.identification.SectionDao;
import ci.gouv.budget.solde.sigdcp.model.identification.Section;
import ci.gouv.budget.solde.sigdcp.service.DefaultServiceImpl;

@Stateless
public class SectionServiceImpl extends DefaultServiceImpl<Section, String> implements SectionService {
	
	private static final long serialVersionUID = 1170771216036513138L;

	@Inject
	public SectionServiceImpl(SectionDao dao) {
		super(dao);
	}

}
