package ci.gouv.budget.solde.sigdcp.controller;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import ci.gouv.budget.solde.sigdcp.controller.ui.form.AbstractFormUIController;
import ci.gouv.budget.solde.sigdcp.service.SampleDataService;

@Named @RequestScoped
public class ExecuterParametrageController extends AbstractFormUIController<Object> implements Serializable {

	private static final long serialVersionUID = -2649364185050925278L;

	@Inject private SampleDataService sampleDataService;
	
	@Override
	protected InitWhen initWhen() {
		return InitWhen.POST_CONSTRUCT;
	}
	
	@Override
	protected void initialisation() {
		super.initialisation();
		defaultSubmitCommand.setValue(text("bouton.sampledatacreate"));
		defaultSubmitCommand.setNotificationMessageId("notification.sampledata.created");
		defaultSubmitCommand.onSuccessStayOnCurrentView();
	} 
	
	@Override
	protected void onDefaultSubmitAction() throws Exception {
		sampleDataService.executerParametrage();
	}
	
}
