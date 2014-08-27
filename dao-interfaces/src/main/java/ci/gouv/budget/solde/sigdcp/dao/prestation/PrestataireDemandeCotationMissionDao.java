package ci.gouv.budget.solde.sigdcp.dao.prestation;

import java.util.Collection;

import ci.gouv.budget.solde.sigdcp.dao.DataAccessObject;
import ci.gouv.budget.solde.sigdcp.model.prestation.PrestataireDemandeCotationMission;
import ci.gouv.budget.solde.sigdcp.model.prestation.PrestataireDemandeCotationMissionId;

public interface PrestataireDemandeCotationMissionDao extends DataAccessObject<PrestataireDemandeCotationMission,PrestataireDemandeCotationMissionId> {

	Collection<PrestataireDemandeCotationMission> readByDemandeId(Long demandeId);
	Collection<PrestataireDemandeCotationMission> readAllByPrestataireId(Long prestataireId);
	Collection<PrestataireDemandeCotationMission> readATraiterByPrestataireId(Long prestataireId);
	Collection<PrestataireDemandeCotationMission> readATraiter();
}
