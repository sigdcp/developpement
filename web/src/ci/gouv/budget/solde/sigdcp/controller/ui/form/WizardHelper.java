package ci.gouv.budget.solde.sigdcp.controller.ui.form;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

import org.primefaces.component.wizard.Wizard;
import org.primefaces.event.FlowEvent;

import ci.gouv.budget.solde.sigdcp.controller.ui.form.command.Action;
import ci.gouv.budget.solde.sigdcp.controller.ui.form.command.FormCommand;

@Getter @Setter
public class WizardHelper<OBJECT> extends Wizard implements Serializable {

	private static final long serialVersionUID = -2730788906343116599L;
	
	private AbstractFormUIController<OBJECT> form;
	private String widgetVar;
	private FormCommand<OBJECT> previous,next,submitAction;
	private Integer currentStepIndex=0;
	private String[] stepIds;
	
	public WizardHelper(AbstractFormUIController<OBJECT> form,String...stepIds) {
		setShowNavBar(Boolean.FALSE);
		setShowStepStatus(Boolean.FALSE);
		this.form = form;
		this.widgetVar = form.getWebConstantResources().getWidgetVarWizard();
		this.stepIds = stepIds;
		this.submitAction = form.getDefaultSubmitCommand();
		this.submitAction.setRendered(isShowSubmitAction());
		previous = form.createCommand().init("bouton.precedent","ui-icon-arrowthick-1-w",null,new Action() {
			private static final long serialVersionUID = 1L;
			@Override
			protected void __execute__() throws Exception {
				previous();
			}
		}).onSuccessStayOnCurrentView();
		config(previous, "previous();");
		
		next = form.createCommand().init("bouton.suivant","ui-icon-arrowthick-1-e",null,new Action() {
			private static final long serialVersionUID = -5533784525516672597L;
			@Override
			protected void __execute__() throws Exception {
				next();
			}
		}).onSuccessStayOnCurrentView();
		config(next, String.format("next();"));
	}
	
	private void config(FormCommand<?> button,String script){
		button.setUpdate("@form");
		button.setOncomplete(script);
	}
	
	protected void previous(){
		move(-1);
	}
	
	protected void next(){
		move(1);
	}
	
	protected void move(Integer stepCount){
		currentStepIndex += stepCount; 
		submitAction.setRendered(isShowSubmitAction());
		previous.setRendered(currentStepIndex>0);
		next.setRendered(!submitAction.isRendered());
	}
	
	public Boolean isLastStep(){
		return stepIds.length-1 == currentStepIndex;
	}
	
	public String onFlowProcess(FlowEvent event) { 
		String currentStepId = __onFlowProcess__(event);
		
		System.out.println("WizardHelper.onFlowProcess() : "+event.getNewStep()+" : "+submitAction.isRendered());
		return currentStepId;
	}
	
	protected Boolean isShowSubmitAction(){
		return isLastStep();
	}
	
	protected String __onFlowProcess__(FlowEvent event) { 
		return event.getNewStep();
	}
}