package ci.gouv.budget.solde.sigdcp.dao.geographie;

import java.util.Collection;

import ci.gouv.budget.solde.sigdcp.dao.DataAccessObject;
import ci.gouv.budget.solde.sigdcp.model.geographie.Localite;

public interface LocaliteDao extends DataAccessObject<Localite,String> {

	Collection<Localite> readByTypeId(String typeId);
	
	Collection<Localite> readByTypeIdByParent(String typeId,Localite parent);

}
