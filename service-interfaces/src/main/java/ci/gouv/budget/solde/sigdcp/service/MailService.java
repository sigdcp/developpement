package ci.gouv.budget.solde.sigdcp.service;

import java.util.Collection;

import ci.gouv.budget.solde.sigdcp.model.MailMessage;

public interface MailService {
	
	void send(MailMessage message,String[] receivers);
	
	void send(MailMessage message,String receiver);
	
	void send(MailMessage message,Collection<String> receivers);

}
