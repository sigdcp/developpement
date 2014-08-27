package ci.gouv.budget.solde.sigdcp.service;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class AbstractServiceException extends RuntimeException implements Serializable {

	private static final long serialVersionUID = 5016330302249841870L;

	private String messageId;
	
	public AbstractServiceException() {
		super();
	}

	public AbstractServiceException(String message, Throwable cause,boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public AbstractServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	public AbstractServiceException(String message) {
		super(message);
	}

	public AbstractServiceException(Throwable cause) {
		super(cause);
	}

	@Override
	public String toString() {
		return getMessage();
	}
	
	

}
