package ci.gouv.budget.solde.sigdcp.service.utils.communication;

import java.util.Collection;

import ci.gouv.budget.solde.sigdcp.model.communication.NotificationMessage;
import ci.gouv.budget.solde.sigdcp.model.identification.Party;

public interface MailService {
	
	void send(NotificationMessage message,String[] receivers);
	
	void send(NotificationMessage message,String receiver);
	
	void send(NotificationMessage message,Collection<String> receivers);
	
	
	
	void send(NotificationMessage message,Party[] receivers);
	
	void send(NotificationMessage message,Party receiver);
	
	void sendParty(NotificationMessage message,Collection<Party> receivers);

}
