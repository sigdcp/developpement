package ci.gouv.budget.solde.sigdcp.service.dossier;

import java.io.Serializable;

import javax.inject.Inject;

import ci.gouv.budget.solde.sigdcp.dao.dossier.AbstractDeplacementDao;
import ci.gouv.budget.solde.sigdcp.model.dossier.Deplacement;
import ci.gouv.budget.solde.sigdcp.service.DefaultServiceImpl;


public abstract class AbstractDeplacementServiceImpl<DEPLACEMENT extends Deplacement> extends DefaultServiceImpl<DEPLACEMENT, Long> implements AbstractDeplacementService<DEPLACEMENT>,Serializable {
	
	private static final long serialVersionUID = -7765679080076677680L;
	
	@Inject
	public AbstractDeplacementServiceImpl(AbstractDeplacementDao<DEPLACEMENT> dao) {
		super(dao);
	}
	
}
