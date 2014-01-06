package ci.gouv.budget.solde.sigdcp.service.utils;

import ci.gouv.budget.solde.sigdcp.service.ServiceException;

public class ServiceUtils {
	
	public static void throwNotYetImplemented(){
		throw new ServiceException("Ce service n'a pas encore été implémenté!");
	}

}
