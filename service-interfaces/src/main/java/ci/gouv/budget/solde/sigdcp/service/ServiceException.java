package ci.gouv.budget.solde.sigdcp.service;

import java.io.Serializable;

import javax.ejb.ApplicationException;

@ApplicationException(rollback=true)
public class ServiceException extends AbstractServiceException implements Serializable {

	private static final long serialVersionUID = 5016330302249841870L;
	
	public ServiceException() {
		super();
	}

	public ServiceException(String message, Throwable cause,boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public ServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	public ServiceException(String message) {
		super(message);
	}

	public ServiceException(Throwable cause) {
		super(cause);
	}

	@Override
	public String toString() {
		return getMessage();
	}
	
	

}
