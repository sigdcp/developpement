package ci.gouv.budget.solde.sigdcp.service.communication;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ci.gouv.budget.solde.sigdcp.model.communication.NotificationMessage;

@AllArgsConstructor @Getter @Setter
public abstract class AbstractNotificationEvent implements Serializable {

	private static final long serialVersionUID = 3162278587615246394L;

	protected NotificationMessage message;
	
}
