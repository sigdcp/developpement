package ci.gouv.budget.solde.sigdcp.dao.calendrier;

import ci.gouv.budget.solde.sigdcp.dao.DataAccessObject;
import ci.gouv.budget.solde.sigdcp.model.calendrier.Mission;
import ci.gouv.budget.solde.sigdcp.model.identification.Personne;

public interface MissionDao extends DataAccessObject<Mission,Long> {

	Mission readSaisieByPersonne(Personne personne);
	
}
