package ci.gouv.budget.solde.sigdcp.dao.calendrier;

import java.util.Collection;

import ci.gouv.budget.solde.sigdcp.dao.DataAccessObject;
import ci.gouv.budget.solde.sigdcp.model.calendrier.CalendrierMission;
import ci.gouv.budget.solde.sigdcp.model.calendrier.Exercice;
import ci.gouv.budget.solde.sigdcp.model.identification.Section;

public interface CalendrierMissionDao extends DataAccessObject<CalendrierMission,Long> {

	Collection<CalendrierMission> readByExercice(Exercice exercice);
	Collection<CalendrierMission> readByMinistere(Section ministere);

}
