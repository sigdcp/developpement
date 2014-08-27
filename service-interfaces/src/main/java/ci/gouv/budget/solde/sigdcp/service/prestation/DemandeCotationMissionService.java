package ci.gouv.budget.solde.sigdcp.service.prestation;

import ci.gouv.budget.solde.sigdcp.model.calendrier.MissionExecutee;
import ci.gouv.budget.solde.sigdcp.model.prestation.DemandeCotationMission;
import ci.gouv.budget.solde.sigdcp.service.traitement.TraitableService;

public interface DemandeCotationMissionService extends TraitableService<DemandeCotationMission,Long> {

	DemandeCotationMission nouveau(MissionExecutee mission);
	//Collection<DemandeCotationMission>findAComander();
}
