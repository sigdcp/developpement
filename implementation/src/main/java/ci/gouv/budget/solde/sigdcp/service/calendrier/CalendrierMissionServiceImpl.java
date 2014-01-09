package ci.gouv.budget.solde.sigdcp.service.calendrier;

import java.io.Serializable;

import javax.ejb.Stateless;
import javax.inject.Inject;

import ci.gouv.budget.solde.sigdcp.dao.calendrier.CalendrierMissionDao;
import ci.gouv.budget.solde.sigdcp.model.calendrier.CalendrierMission;
import ci.gouv.budget.solde.sigdcp.service.DefaultServiceImpl;

@Stateless
public class CalendrierMissionServiceImpl extends DefaultServiceImpl<CalendrierMission, Long> implements CalendrierMissionService , Serializable {

	private static final long serialVersionUID = -7601857525393731774L;
	
	
	@Inject
	public CalendrierMissionServiceImpl(CalendrierMissionDao dao) {
		super(dao);
	}
	

	
}
