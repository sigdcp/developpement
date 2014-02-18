package ci.gouv.budget.solde.sigdcp.service.calendrier;

import java.util.Collection;

import ci.gouv.budget.solde.sigdcp.model.calendrier.Mission;
import ci.gouv.budget.solde.sigdcp.model.dossier.DossierMission;
import ci.gouv.budget.solde.sigdcp.model.dossier.PieceJustificative;
import ci.gouv.budget.solde.sigdcp.model.identification.Personne;
import ci.gouv.budget.solde.sigdcp.service.AbstractService;

public interface MissionService extends AbstractService<Mission,Long> {

	void enregistrer(Mission mission,Collection<DossierMission> dossiers,Collection<Collection<PieceJustificative>> pieceJustificatives);
	
	void soumettre(Mission mission,Collection<DossierMission> dossiers,Collection<Collection<PieceJustificative>> pieceJustificatives);
	
	Mission findSaisieByPersonne(Personne personne);
	
}
