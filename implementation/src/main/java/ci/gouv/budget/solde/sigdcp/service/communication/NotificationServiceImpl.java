package ci.gouv.budget.solde.sigdcp.service.communication;

import java.io.Serializable;
import java.util.Map;

import javax.enterprise.event.Event;
import javax.inject.Inject;

import ci.gouv.budget.solde.sigdcp.model.communication.NotificationMessage;
import ci.gouv.budget.solde.sigdcp.model.communication.NotificationMessageType;
import ci.gouv.budget.solde.sigdcp.model.identification.Party;
import ci.gouv.budget.solde.sigdcp.service.utils.TemplateEngineService;
import ci.gouv.budget.solde.sigdcp.service.utils.communication.MailService;
import ci.gouv.budget.solde.sigdcp.service.utils.communication.NotificationService;

public class NotificationServiceImpl implements NotificationService,Serializable {

	private static final long serialVersionUID = -4376077455219565698L;
	
	private static final Boolean mail = Boolean.FALSE;
	private static final Boolean fire = Boolean.FALSE;
	
	@Inject private MailService mailService;
	@Inject private TemplateEngineService templateEngineService;
	
	@Inject private Event<NotificationEvent> eventService;

	@Override
	public NotificationMessage build(NotificationMessageType messageType,Map<String, Object> parameters) {
		NotificationMessage message = new NotificationMessage(messageType.getSubject(), 
				templateEngineService.find(messageType.getEmailTemplateId(), parameters));
		return message;
	}
	
	@Override
	public NotificationMessage send(NotificationMessageType messageType,Map<String, Object> parameters, String receiver) {
		NotificationMessage message = new NotificationMessage(messageType.getSubject(), 
				templateEngineService.find(messageType.getEmailTemplateId(), parameters));
		if(mail)
			mailService.send(message, receiver);
		
		//when mail not working use console for testing
		//System.out.println(message);
		
		if(fire)
			eventService.fire(new NotificationEvent(message));
		return message;
	}
	
	@Override
	public NotificationMessage send(NotificationMessageType messageType,Map<String, Object> parameters, Party receiver) {
		return send(messageType, parameters, receiver.getContact().getEmail());
	}

}
