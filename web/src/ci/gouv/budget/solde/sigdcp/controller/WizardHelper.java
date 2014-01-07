package ci.gouv.budget.solde.sigdcp.controller;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

import org.primefaces.component.wizard.Wizard;
import org.primefaces.event.FlowEvent;

@Getter @Setter
public class WizardHelper<OBJECT> extends Wizard implements Serializable {

	private static final long serialVersionUID = -2730788906343116599L;
	
	private AbstractFormUIController<OBJECT> form;
	private String widgetVar;
	private AbstractFormSubmitAction<OBJECT> previous,next,submitAction;
	private Integer currentStepIndex=0;
	private String[] stepIds;
	
	public WizardHelper(AbstractFormUIController<OBJECT> form,String...stepIds) {
		setShowNavBar(Boolean.FALSE);
		setShowStepStatus(Boolean.FALSE);
		this.form = form;
		this.widgetVar = form.getWebConstantResources().getWidgetVarWizard();
		this.stepIds = stepIds;
		this.submitAction = form.getDefaultSubmitAction();
		this.submitAction.setRendered(isShowSubmitAction());
		previous = new AbstractFormSubmitAction<OBJECT>(form,"bouton.precedent","ui-icon-arrowthick-1-w",null,Boolean.TRUE,Boolean.FALSE,NavigationManager.OUTCOME_CURRENT_VIEW) {
			private static final long serialVersionUID = -2683422739395829063L;
			@Override protected void action() {
				previous();
			}
		};
		config(previous, "previous();");
		next = new AbstractFormSubmitAction<OBJECT>(form,"bouton.suivant","ui-icon-arrowthick-1-e",null,Boolean.TRUE,Boolean.TRUE,NavigationManager.OUTCOME_CURRENT_VIEW) {
			private static final long serialVersionUID = -2683422739395829063L;
			@Override protected void action() {
				next();
			}
		};
		config(next, String.format("next();"));
	}
	
	private void config(AbstractFormSubmitAction<?> button,String script){
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
