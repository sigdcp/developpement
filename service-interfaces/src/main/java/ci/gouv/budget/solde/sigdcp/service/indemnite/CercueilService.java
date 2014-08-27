package ci.gouv.budget.solde.sigdcp.service.indemnite;

import java.math.BigDecimal;

import ci.gouv.budget.solde.sigdcp.model.indemnite.Cercueil;
import ci.gouv.budget.solde.sigdcp.service.AbstractService;

public interface CercueilService extends AbstractService<Cercueil,String> {

	Cercueil findByIndice(BigDecimal indice);
	
}
