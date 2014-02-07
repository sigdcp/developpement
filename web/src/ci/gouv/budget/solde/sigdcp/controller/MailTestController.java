package ci.gouv.budget.solde.sigdcp.controller;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import lombok.Getter;
import lombok.Setter;
import ci.gouv.budget.solde.sigdcp.controller.ui.form.AbstractFormUIController;
import ci.gouv.budget.solde.sigdcp.controller.ui.form.command.Action;
import ci.gouv.budget.solde.sigdcp.model.communication.NotificationMessage;
import ci.gouv.budget.solde.sigdcp.model.communication.NotificationMessageType;
import ci.gouv.budget.solde.sigdcp.service.utils.communication.NotificationService;

@Named @ViewScoped
public class MailTestController extends AbstractFormUIController<Object> implements Serializable {

	private static final long serialVersionUID = -2649364185050925278L;

	@Inject private NotificationService notificationService;
	
	
	@Getter @Setter
	private String receiver="christian.komenan@budget.gouv.ci",message;
	
	@Override
	protected InitWhen initWhen() {
		return InitWhen.POST_CONSTRUCT;
	}
	
	@Override
	protected void initialisation() {
		super.initialisation();
		defaultSubmitCommand.setNotificationMessageId("notification.mail.sent");
		//command = createCommand().init("", icon, notificationMessageId, _action);
		/*
		defaultSubmitCommand.set_notificationMessage(new Action() {
			private static final long serialVersionUID = -4484470706738582046L;
			@Override
			protected Object __execute__(Object object) throws Exception {
				return notificationService.build(NotificationMessageType.AVIS_COMPTE_UTILISATEUR_VERROUILLE_ACCES_MULTIPLE, null).toString();
			}
		});*/
	} 
	
	@Override
	protected void onDefaultSubmitAction() throws Exception {
		NotificationMessage message = notificationService.send(NotificationMessageType._TEST_, null, receiver); 
		//messageManager.addInfo(notificationService.build(NotificationMessageType.AVIS_COMPTE_UTILISATEUR_VERROUILLE_ACCES_MULTIPLE, null).toString(), false);

	}
	
}
