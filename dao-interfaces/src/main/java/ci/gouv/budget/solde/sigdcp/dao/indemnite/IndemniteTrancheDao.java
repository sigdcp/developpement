package ci.gouv.budget.solde.sigdcp.dao.indemnite;

import java.math.BigDecimal;

import ci.gouv.budget.solde.sigdcp.dao.DataAccessObject;
import ci.gouv.budget.solde.sigdcp.model.indemnite.IndemniteTranche;
import ci.gouv.budget.solde.sigdcp.model.indemnite.TypeIndemniteTranche;

public interface IndemniteTrancheDao extends DataAccessObject<IndemniteTranche,Long> {

	IndemniteTranche readByValeurByType(BigDecimal valeur,TypeIndemniteTranche type);
	

}
