package ci.gouv.budget.solde.sigdcp.service.calendrier;

import ci.gouv.budget.solde.sigdcp.model.calendrier.Exercice;
import ci.gouv.budget.solde.sigdcp.service.AbstractService;

public interface ExerciceService extends AbstractService<Exercice,Integer> {

	Exercice findCurrent();
	
}
