package ci.gouv.budget.solde.sigdcp.service.calendrier;

import java.io.Serializable;
import java.util.Collection;

import javax.inject.Inject;

import ci.gouv.budget.solde.sigdcp.dao.calendrier.CalendrierMissionDao;
import ci.gouv.budget.solde.sigdcp.model.calendrier.CalendrierMission;
import ci.gouv.budget.solde.sigdcp.model.calendrier.Exercice;
import ci.gouv.budget.solde.sigdcp.model.identification.Section;
import ci.gouv.budget.solde.sigdcp.service.DefaultServiceImpl;

public class CalendrierMissionServiceImpl extends DefaultServiceImpl<CalendrierMission, Long> implements CalendrierMissionService , Serializable {

	private static final long serialVersionUID = -7601857525393731774L;
	
	
	@Inject
	public CalendrierMissionServiceImpl(CalendrierMissionDao dao) {
		super(dao);
	}


	@Override
	public Collection<CalendrierMission> findByExercice(Exercice exercice) {
		return ((CalendrierMissionDao)dao).readByExercice(exercice);
	}


	@Override
	public Collection<CalendrierMission> findByMinistere(Section ministere) {
		return ((CalendrierMissionDao)dao).readByMinistere(ministere);
	}


	
	

	
}
