package ci.gouv.budget.solde.sigdcp.dao.indemnite;

import ci.gouv.budget.solde.sigdcp.dao.DataAccessObject;
import ci.gouv.budget.solde.sigdcp.model.geographie.Localite;
import ci.gouv.budget.solde.sigdcp.model.indemnite.GroupeMission;
import ci.gouv.budget.solde.sigdcp.model.indemnite.LocaliteGroupeMission;
import ci.gouv.budget.solde.sigdcp.model.indemnite.LocaliteGroupeMissionId;

public interface LocaliteGroupeMissionDao extends DataAccessObject<LocaliteGroupeMission,LocaliteGroupeMissionId> {

	LocaliteGroupeMission readByLocaliteByGroupe(Localite localite,GroupeMission groupeMission);
	

} 
