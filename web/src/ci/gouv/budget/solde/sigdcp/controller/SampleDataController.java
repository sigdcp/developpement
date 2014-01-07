package ci.gouv.budget.solde.sigdcp.controller;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import ci.gouv.budget.solde.sigdcp.service.SampleDataService;

@Named @RequestScoped
public class SampleDataController extends AbstractFormUIController<Object> implements Serializable {

	private static final long serialVersionUID = -2649364185050925278L;

	@Inject private SampleDataService sampleDataService;
	
	@Override
	protected void postConstruct() {
		super.postConstruct();
		defaultSubmitAction = new AbstractFormSubmitAction<Object>(this,"bouton.sampledatacreate","ui-icon-check","notification.sampledata.created",
				Boolean.FALSE,Boolean.TRUE) {
			private static final long serialVersionUID = -2683422739395829063L;
			@Override
			protected void action() {
				sampleDataService.create();
			}
		};
	}
	
	@Override
	public boolean isCreate() {
		return Boolean.TRUE;
	}
	
}
