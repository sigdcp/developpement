package ci.gouv.budget.solde.sigdcp.service.calendrier;

import java.util.Collection;

import ci.gouv.budget.solde.sigdcp.model.calendrier.CalendrierMission;
import ci.gouv.budget.solde.sigdcp.model.calendrier.Exercice;
import ci.gouv.budget.solde.sigdcp.model.identification.Section;
import ci.gouv.budget.solde.sigdcp.service.AbstractService;

public interface CalendrierMissionService extends AbstractService<CalendrierMission,Long> {

	Collection<CalendrierMission> findByExercice(Exercice exercice);
	Collection<CalendrierMission> findByMinistere(Section ministere);
	
}
