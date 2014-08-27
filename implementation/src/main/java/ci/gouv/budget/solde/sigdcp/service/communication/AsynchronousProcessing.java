package ci.gouv.budget.solde.sigdcp.service.communication;

import java.io.Serializable;
import java.util.logging.Level;

import javax.enterprise.event.Observes;
import javax.enterprise.event.TransactionPhase;
import javax.inject.Inject;
import javax.inject.Singleton;

import lombok.extern.java.Log;
import ci.gouv.budget.solde.sigdcp.service.utils.communication.MailService;

@Log @Singleton
public class AsynchronousProcessing implements Serializable {

	private static final long serialVersionUID = -7953818474046227664L;
	@Inject private MailService mailService;
	
	public void onSystemNotificationEvent(@Observes(during = TransactionPhase.AFTER_COMPLETION) SystemNotificationEvent notificationEvent) {
		System.out
				.println("AsynchronousProcessing.onSystemNotificationEvent()");
		System.out.println(notificationEvent.getMessage());
	}
	
	public void onMailNotificationEvent(@Observes(during = TransactionPhase.AFTER_COMPLETION) MailNotificationEvent notificationEvent) {
		try {
			mailService.send(notificationEvent.getMessage(), notificationEvent.getReceiver());
		} catch (Exception e) {
			log.log(Level.WARNING, e.toString());
		}
	}
	
}
