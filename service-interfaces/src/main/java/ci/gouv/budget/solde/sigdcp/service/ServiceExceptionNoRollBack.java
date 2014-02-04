package ci.gouv.budget.solde.sigdcp.service;

import java.io.Serializable;

import javax.ejb.ApplicationException;

@ApplicationException(rollback=false)
public class ServiceExceptionNoRollBack extends AbstractServiceException implements Serializable {

	private static final long serialVersionUID = 5016330302249841870L;

	public ServiceExceptionNoRollBack() {
		super();
	}

	public ServiceExceptionNoRollBack(String message, Throwable cause,boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public ServiceExceptionNoRollBack(String message, Throwable cause) {
		super(message, cause);
	}

	public ServiceExceptionNoRollBack(String message) {
		super(message);
	}

	public ServiceExceptionNoRollBack(Throwable cause) {
		super(cause);
	}

	@Override
	public String toString() {
		return getMessage();
	}
	
	

}
