package ci.gouv.budget.solde.sigdcp.service.calendrier;

import java.util.Collection;

import ci.gouv.budget.solde.sigdcp.model.calendrier.MissionExecutee;
import ci.gouv.budget.solde.sigdcp.model.dossier.DossierMission;
import ci.gouv.budget.solde.sigdcp.model.dossier.PieceJustificative;
import ci.gouv.budget.solde.sigdcp.model.identification.Personne;
import ci.gouv.budget.solde.sigdcp.service.AbstractService;
import ci.gouv.budget.solde.sigdcp.service.ActionType;

public interface MissionExecuteeService extends AbstractService<MissionExecutee,Long> {

	void enregistrer(ActionType actionType,MissionExecutee missionExecutee,Collection<DossierMission> dossiers,Collection<Collection<PieceJustificative>> pieceJustificatives);
	
	MissionExecutee findSaisieByPersonne(Personne personne);
	
}
