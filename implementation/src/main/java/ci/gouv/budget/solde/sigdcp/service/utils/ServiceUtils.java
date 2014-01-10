package ci.gouv.budget.solde.sigdcp.service.utils;

import javax.inject.Inject;

import lombok.Getter;
import ci.gouv.budget.solde.sigdcp.dao.PersistenceUtils;
import ci.gouv.budget.solde.sigdcp.service.ServiceException;

public class ServiceUtils {
	
	@Inject @Getter private PersistenceUtils persistenceUtils;
	
	public void throwNotYetImplemented(){
		throw new ServiceException("Ce service n'a pas encore été implémenté!");
	}
	
	
}
