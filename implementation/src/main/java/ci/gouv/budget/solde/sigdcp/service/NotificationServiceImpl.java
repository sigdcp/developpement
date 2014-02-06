package ci.gouv.budget.solde.sigdcp.service;

import java.io.Serializable;
import java.util.Map;

import javax.inject.Inject;

import lombok.extern.java.Log;
import ci.gouv.budget.solde.sigdcp.model.identification.Party;
import ci.gouv.budget.solde.sigdcp.service.utils.TemplateEngineService;
import ci.gouv.budget.solde.sigdcp.service.utils.communication.MailService;
import ci.gouv.budget.solde.sigdcp.service.utils.communication.NotificationService;

@Log
public class NotificationServiceImpl implements NotificationService,Serializable {

	private static final long serialVersionUID = -4376077455219565698L;
	
	@Inject private MailService mailService;
	@Inject private TemplateEngineService templateEngineService;
	
	@Override
	public void send(String subject, String messageTemplateId,Map<String, Object> parameters, Party receiver) {
		String messageBody = templateEngineService.find(messageTemplateId, parameters);
		//mailService.send(new MailMessage(subject, messageBody), receiver);
		
		//when mail not working use console for testing
		System.out.println(messageBody);
	}

}
