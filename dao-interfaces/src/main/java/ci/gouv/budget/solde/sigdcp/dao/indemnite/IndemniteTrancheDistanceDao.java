package ci.gouv.budget.solde.sigdcp.dao.indemnite;

import java.math.BigDecimal;

import ci.gouv.budget.solde.sigdcp.dao.DataAccessObject;
import ci.gouv.budget.solde.sigdcp.model.dossier.CategorieDeplacement;
import ci.gouv.budget.solde.sigdcp.model.indemnite.IndemniteTrancheDistance;

public interface IndemniteTrancheDistanceDao extends DataAccessObject<IndemniteTrancheDistance,Long> {

	IndemniteTrancheDistance readByValeurByCategorieDeplacement(CategorieDeplacement categorieDeplacement,BigDecimal valeur);
	

}
