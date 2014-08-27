package ci.gouv.budget.solde.sigdcp.dao.dossier;

import java.util.Collection;

import ci.gouv.budget.solde.sigdcp.model.dossier.DossierMission;
import ci.gouv.budget.solde.sigdcp.model.identification.Personne;

public interface DossierMissionDao extends AbstractDossierDao<DossierMission> {

	Collection<DossierMission> readSaisieByPointFocal(Personne personne);
	
	
	
}
   