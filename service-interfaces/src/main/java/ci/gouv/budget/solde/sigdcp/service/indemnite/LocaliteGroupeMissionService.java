package ci.gouv.budget.solde.sigdcp.service.indemnite;

import ci.gouv.budget.solde.sigdcp.model.geographie.Localite;
import ci.gouv.budget.solde.sigdcp.model.identification.Fonction;
import ci.gouv.budget.solde.sigdcp.model.identification.Grade;
import ci.gouv.budget.solde.sigdcp.model.indemnite.LocaliteGroupeMission;
import ci.gouv.budget.solde.sigdcp.model.indemnite.LocaliteGroupeMissionId;
import ci.gouv.budget.solde.sigdcp.service.AbstractService;

public interface LocaliteGroupeMissionService extends AbstractService<LocaliteGroupeMission,LocaliteGroupeMissionId> {

	LocaliteGroupeMission findByFonctionOrGradeByLocalite(Fonction fonction,Grade grade,Localite localite);
	
}
