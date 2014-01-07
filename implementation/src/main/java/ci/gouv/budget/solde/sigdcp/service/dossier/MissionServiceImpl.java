package ci.gouv.budget.solde.sigdcp.service.dossier;

import java.io.Serializable;

import javax.ejb.Stateless;
import javax.inject.Inject;

import ci.gouv.budget.solde.sigdcp.dao.dossier.MissionDao;
import ci.gouv.budget.solde.sigdcp.model.calendrier.Mission;

@Stateless
public class MissionServiceImpl extends AbstractDeplacementServiceImpl<Mission> implements MissionService,Serializable {
	
	private static final long serialVersionUID = -7765679080076677680L;
	
	@Inject
	public MissionServiceImpl(MissionDao dao) {
		super(dao);
	}

}
 