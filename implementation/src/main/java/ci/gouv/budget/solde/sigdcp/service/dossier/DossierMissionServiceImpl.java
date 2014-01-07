package ci.gouv.budget.solde.sigdcp.service.dossier;

import java.io.Serializable;

import javax.ejb.Stateless;
import javax.inject.Inject;

import ci.gouv.budget.solde.sigdcp.dao.dossier.DossierMissionDao;
import ci.gouv.budget.solde.sigdcp.model.dossier.DossierMission;

@Stateless
public class DossierMissionServiceImpl extends AbstractDossierServiceImpl<DossierMission> implements DossierMissionService,Serializable {
	
	private static final long serialVersionUID = -7765679080076677680L;
	
	@Inject
	public DossierMissionServiceImpl(DossierMissionDao dao) {
		super(dao);
	}
 
}
 