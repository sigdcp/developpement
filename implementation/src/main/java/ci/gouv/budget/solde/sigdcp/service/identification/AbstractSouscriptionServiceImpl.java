package ci.gouv.budget.solde.sigdcp.service.identification;

import java.io.Serializable;
import java.util.Collection;

import ci.gouv.budget.solde.sigdcp.dao.identification.AbstractSouscriptionDao;
import ci.gouv.budget.solde.sigdcp.model.identification.Souscription;
import ci.gouv.budget.solde.sigdcp.service.DefaultServiceImpl;

public abstract class AbstractSouscriptionServiceImpl<SOUSCRIPTION extends Souscription> extends DefaultServiceImpl<SOUSCRIPTION, String> implements AbstractSouscriptionService<SOUSCRIPTION>,Serializable {
	
	private static final long serialVersionUID = 1170771216036513138L;
	
	public AbstractSouscriptionServiceImpl(AbstractSouscriptionDao<SOUSCRIPTION> dao) {
		super(dao);
	}

	@Override
	public Collection<SOUSCRIPTION> findSouscriptionsAValiderByTypePersonneId(String typePersonneId) {
		return null;
	}

	@Override
	public Collection<SOUSCRIPTION> findSouscriptionsAValider() {
		return null;
	}

	
	
	

}
