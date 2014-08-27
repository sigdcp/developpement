package ci.gouv.budget.solde.sigdcp.dao.indemnite;

import java.math.BigDecimal;

import ci.gouv.budget.solde.sigdcp.dao.DataAccessObject;
import ci.gouv.budget.solde.sigdcp.model.indemnite.Cercueil;

public interface CercueilDao extends DataAccessObject<Cercueil,String> {

	Cercueil readByIndice(BigDecimal indice); 

}
