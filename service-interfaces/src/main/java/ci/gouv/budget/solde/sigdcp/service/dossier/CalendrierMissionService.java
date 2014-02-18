package ci.gouv.budget.solde.sigdcp.service.dossier;

import java.util.Collection;

import ci.gouv.budget.solde.sigdcp.model.calendrier.CalendrierMission;
import ci.gouv.budget.solde.sigdcp.model.calendrier.Mission;

public interface CalendrierMissionService extends AbstractDeplacementService<Mission> {
	
	Collection<Mission> findByCalendrier(CalendrierMission calendrier);
	 
}
 