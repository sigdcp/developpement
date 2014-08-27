package ci.gouv.budget.solde.sigdcp.dao.calendrier;

import java.util.Collection;

import ci.gouv.budget.solde.sigdcp.dao.DataAccessObject;
import ci.gouv.budget.solde.sigdcp.model.calendrier.MissionExecutee;
import ci.gouv.budget.solde.sigdcp.model.dossier.DossierMission;
import ci.gouv.budget.solde.sigdcp.model.identification.Personne;

public interface MissionExecuteeDao extends DataAccessObject<MissionExecutee,Long> {

	MissionExecutee readSaisieByPersonne(Personne personne);
	
	MissionExecutee readByDossier(DossierMission dossierMission);
	
	Collection<MissionExecutee> readByPersonne(Personne personne);

	Collection<MissionExecutee> readACoter();
	
}
