package ci.gouv.budget.solde.sigdcp.dao.indemnite;

import java.math.BigDecimal;

import ci.gouv.budget.solde.sigdcp.dao.DataAccessObject;
import ci.gouv.budget.solde.sigdcp.model.indemnite.IndemniteTrancheCercueil;

public interface IndemniteTrancheCercueilDao extends DataAccessObject<IndemniteTrancheCercueil,Long> {

	IndemniteTrancheCercueil readByIndice(BigDecimal indice);
	
}
