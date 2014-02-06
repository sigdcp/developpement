package ci.gouv.budget.solde.sigdcp.service.utils.communication;

import java.util.Collection;

import ci.gouv.budget.solde.sigdcp.model.MailMessage;
import ci.gouv.budget.solde.sigdcp.model.identification.Party;

public interface MailService {
	
	void send(MailMessage message,String[] receivers);
	
	void send(MailMessage message,String receiver);
	
	void send(MailMessage message,Collection<String> receivers);
	
	
	
	void send(MailMessage message,Party[] receivers);
	
	void send(MailMessage message,Party receiver);
	
	void sendParty(MailMessage message,Collection<Party> receivers);

}
