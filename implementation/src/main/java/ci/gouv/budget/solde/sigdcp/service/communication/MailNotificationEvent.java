package ci.gouv.budget.solde.sigdcp.service.communication;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import ci.gouv.budget.solde.sigdcp.model.communication.NotificationMessage;

@Getter @Setter
public class MailNotificationEvent extends AbstractNotificationEvent implements Serializable {

	private static final long serialVersionUID = 3162278587615246394L;

	private String receiver;
	
	public MailNotificationEvent(NotificationMessage message,String receiver) {
		super(message);
		this.receiver = receiver;
	}
	
}
