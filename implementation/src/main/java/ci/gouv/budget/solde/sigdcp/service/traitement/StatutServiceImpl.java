package ci.gouv.budget.solde.sigdcp.service.traitement;

import java.io.Serializable;

import javax.inject.Inject;

import ci.gouv.budget.solde.sigdcp.dao.traitement.StatutDao;
import ci.gouv.budget.solde.sigdcp.model.traitement.Statut;
import ci.gouv.budget.solde.sigdcp.service.DefaultServiceImpl;
import ci.gouv.budget.solde.sigdcp.service.dossier.StatutService;


public class StatutServiceImpl extends DefaultServiceImpl<Statut,String> implements StatutService , Serializable {

	private static final long serialVersionUID = -7601857525393731774L;
	
	@Inject
	public StatutServiceImpl(StatutDao dao) {
		super(dao);
	}

}
