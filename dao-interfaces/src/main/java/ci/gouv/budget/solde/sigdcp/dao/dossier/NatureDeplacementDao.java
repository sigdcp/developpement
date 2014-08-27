package ci.gouv.budget.solde.sigdcp.dao.dossier;

import java.util.Collection;

import ci.gouv.budget.solde.sigdcp.dao.DataAccessObject;
import ci.gouv.budget.solde.sigdcp.model.dossier.NatureDeplacement;

public interface NatureDeplacementDao extends DataAccessObject<NatureDeplacement,String> {

	Collection<NatureDeplacement> readByCategorieId(String categorieId);

	
}
