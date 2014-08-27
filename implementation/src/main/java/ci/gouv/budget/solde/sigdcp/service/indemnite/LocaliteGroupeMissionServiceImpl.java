package ci.gouv.budget.solde.sigdcp.service.indemnite;

import java.io.Serializable;

import javax.inject.Inject;

import ci.gouv.budget.solde.sigdcp.dao.indemnite.LocaliteGroupeMissionDao;
import ci.gouv.budget.solde.sigdcp.model.geographie.Localite;
import ci.gouv.budget.solde.sigdcp.model.identification.Fonction;
import ci.gouv.budget.solde.sigdcp.model.identification.Grade;
import ci.gouv.budget.solde.sigdcp.model.indemnite.LocaliteGroupeMission;
import ci.gouv.budget.solde.sigdcp.model.indemnite.LocaliteGroupeMissionId;
import ci.gouv.budget.solde.sigdcp.service.DefaultServiceImpl;

public class LocaliteGroupeMissionServiceImpl extends DefaultServiceImpl<LocaliteGroupeMission,LocaliteGroupeMissionId> implements LocaliteGroupeMissionService , Serializable {

	private static final long serialVersionUID = -7601857525393731774L;
	
	@Inject private GroupeMissionService groupeMissionService;
	
	@Inject
	public LocaliteGroupeMissionServiceImpl(LocaliteGroupeMissionDao dao) {
		super(dao);
	}

	@Override
	public LocaliteGroupeMission findByFonctionOrGradeByLocalite(Fonction fonction, Grade grade, Localite localite) {
		return ((LocaliteGroupeMissionDao)dao).readByLocaliteByGroupe(localite, groupeMissionService.findByFonctionOrGrade(fonction, grade));
	}

}
