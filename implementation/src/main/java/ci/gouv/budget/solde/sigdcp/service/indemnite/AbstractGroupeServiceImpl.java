package ci.gouv.budget.solde.sigdcp.service.indemnite;

import java.io.Serializable;

import ci.gouv.budget.solde.sigdcp.dao.dossier.AbstractGroupeDao;
import ci.gouv.budget.solde.sigdcp.model.indemnite.Groupe;
import ci.gouv.budget.solde.sigdcp.service.DefaultServiceImpl;

//@Log
public abstract class AbstractGroupeServiceImpl<GROUPE extends Groupe> extends DefaultServiceImpl<GROUPE, String> implements AbstractGroupeService<GROUPE>,Serializable {
	
	private static final long serialVersionUID = -7765679080076677680L;
	
	public AbstractGroupeServiceImpl(AbstractGroupeDao<GROUPE> dao) {
		super(dao);
	}
	
}
