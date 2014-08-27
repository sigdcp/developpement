package ci.gouv.budget.solde.sigdcp.dao.prestation;

import java.util.Collection;

import ci.gouv.budget.solde.sigdcp.dao.traitement.TraitableDao;
import ci.gouv.budget.solde.sigdcp.model.prestation.DemandeCotationObseque;

public interface DemandeCotationObsequeDao extends TraitableDao<DemandeCotationObseque,Long> {

	Collection<DemandeCotationObseque> readACommander();


}
