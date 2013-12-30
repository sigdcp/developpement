package ci.gouv.budget.solde.sigdcp.dao.dossier;

import java.util.Collection;

import ci.gouv.budget.solde.sigdcp.dao.DataAccessObject;
import ci.gouv.budget.solde.sigdcp.model.geographie.Localite;

public interface LocaliteDao extends DataAccessObject<Localite,String> {

	Collection<Localite> findByTypeId(String typeId);
	

}
