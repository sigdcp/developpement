package ci.gouv.budget.solde.sigdcp.dao.prestation;

import java.util.Collection;

import ci.gouv.budget.solde.sigdcp.dao.traitement.TraitableDao;
import ci.gouv.budget.solde.sigdcp.model.prestation.DemandeCotationMission;

public interface DemandeCotationMissionDao extends TraitableDao<DemandeCotationMission,Long> {

	Collection <DemandeCotationMission> readACommander();
}
