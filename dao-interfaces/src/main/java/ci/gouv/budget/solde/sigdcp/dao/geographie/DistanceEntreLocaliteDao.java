package ci.gouv.budget.solde.sigdcp.dao.geographie;

import ci.gouv.budget.solde.sigdcp.dao.DataAccessObject;
import ci.gouv.budget.solde.sigdcp.model.geographie.DistanceEntreLocalite;
import ci.gouv.budget.solde.sigdcp.model.geographie.DistanceEntreLocaliteId;
import ci.gouv.budget.solde.sigdcp.model.geographie.Localite;

public interface DistanceEntreLocaliteDao extends DataAccessObject<DistanceEntreLocalite,DistanceEntreLocaliteId> {

	DistanceEntreLocalite readByLocalite1ByLocalite2(Localite localite1,Localite localite2);
	
}
