package ci.gouv.budget.solde.sigdcp.controller;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import lombok.Getter;
import lombok.Setter;
import ci.gouv.budget.solde.sigdcp.controller.ui.form.AbstractFormUIController;
import ci.gouv.budget.solde.sigdcp.model.MailMessage;
import ci.gouv.budget.solde.sigdcp.service.utils.communication.MailService;

@Named @RequestScoped
public class MailTestController extends AbstractFormUIController<Object> implements Serializable {

	private static final long serialVersionUID = -2649364185050925278L;

	@Inject private MailService mailService;
	
	@Getter @Setter
	private String receiver,message;
	
	@Override
	protected InitWhen initWhen() {
		return InitWhen.POST_CONSTRUCT;
	}
	
	@Override
	protected void initialisation() {
		super.initialisation();
		defaultSubmitCommand.setNotificationMessageId("notification.mail.sent");
	} 
	
	@Override
	protected void onDefaultSubmitAction() throws Exception {
		mailService.send(new MailMessage("Test Mail", message), receiver);
	}
	
}
