package ci.gouv.budget.solde.sigdcp.service.calendrier;

import java.util.Collection;

import ci.gouv.budget.solde.sigdcp.model.calendrier.MissionExecutee;
import ci.gouv.budget.solde.sigdcp.model.dossier.DossierMission;
import ci.gouv.budget.solde.sigdcp.service.AbstractService;
import ci.gouv.budget.solde.sigdcp.service.ActionType;

public interface MissionExecuteeService extends AbstractService<MissionExecutee,Long> {

	void enregistrer(ActionType actionType,MissionExecutee missionExecutee);
	
	MissionExecutee findSaisieByNumero(Long id);
	
	Collection<MissionExecutee> findMissionOrganisees();
	
	MissionExecutee findByDossier(DossierMission dossierMission);
	
	void transmettreDossier(Collection<DossierMission> dossiers);
	
	//Collection<MissionExecutee> findATraiter(String natureOperationCode);
	
	Collection<MissionExecutee> findACoter();
	
	void init(MissionExecutee missionExecutee,String natureOperation);
	
}
