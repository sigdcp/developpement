package ci.gouv.budget.solde.sigdcp.service.dossier;

import java.io.Serializable;

import javax.ejb.Stateless;
import javax.inject.Inject;

import ci.gouv.budget.solde.sigdcp.dao.dossier.StatutDao;
import ci.gouv.budget.solde.sigdcp.model.dossier.Statut;
import ci.gouv.budget.solde.sigdcp.service.DefaultServiceImpl;

@Stateless
public class StatutServiceImpl extends DefaultServiceImpl<Statut,String> implements StatutService , Serializable {

	private static final long serialVersionUID = -7601857525393731774L;
	
	@Inject
	public StatutServiceImpl(StatutDao dao) {
		super(dao);
	}

}
