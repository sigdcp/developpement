package ci.gouv.budget.solde.sigdcp.service.prestation;

import java.util.Collection;

import ci.gouv.budget.solde.sigdcp.model.prestation.PrestataireDemandeCotationMission;
import ci.gouv.budget.solde.sigdcp.model.prestation.PrestataireDemandeCotationMissionId;
import ci.gouv.budget.solde.sigdcp.service.AbstractService;

public interface PrestataireDemandeCotationMissionService extends AbstractService<PrestataireDemandeCotationMission,PrestataireDemandeCotationMissionId> {
	
	PrestataireDemandeCotationMission nouveau(PrestataireDemandeCotationMission demande);
	Collection<PrestataireDemandeCotationMission> findATraiter(String natureOperationCode);
	void enregistrer(PrestataireDemandeCotationMission demande);
	void init(PrestataireDemandeCotationMission demande);
	
	
}
