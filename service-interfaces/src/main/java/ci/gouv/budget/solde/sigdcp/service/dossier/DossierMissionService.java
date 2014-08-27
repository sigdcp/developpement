package ci.gouv.budget.solde.sigdcp.service.dossier;

import java.util.Collection;

import ci.gouv.budget.solde.sigdcp.model.calendrier.MissionExecutee;
import ci.gouv.budget.solde.sigdcp.model.dossier.DossierMission;
import ci.gouv.budget.solde.sigdcp.model.dossier.PieceJustificative;
import ci.gouv.budget.solde.sigdcp.service.ServiceException;

public interface DossierMissionService extends AbstractDossierService<DossierMission> {
	
	DossierMission nouveau(String matricule,MissionExecutee missionExecutee);
	
	void retournerFeuilleDeplacment(Collection<PieceJustificative> pieceJustificatives) throws ServiceException;
	 
}
 