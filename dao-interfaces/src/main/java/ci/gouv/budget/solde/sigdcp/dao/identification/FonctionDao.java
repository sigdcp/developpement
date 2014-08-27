package ci.gouv.budget.solde.sigdcp.dao.identification;

import java.util.Collection;

import ci.gouv.budget.solde.sigdcp.dao.DataAccessObject;
import ci.gouv.budget.solde.sigdcp.model.identification.Fonction;

public interface FonctionDao extends DataAccessObject<Fonction,String> {

	Collection<Fonction> readInGroupeMissions();

}
 