package ci.gouv.budget.solde.sigdcp.controller;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

import org.primefaces.event.FlowEvent;

@Getter @Setter
public class WizardHelper<OBJECT> implements Serializable {

	private static final long serialVersionUID = -2730788906343116599L;
	/*
	public static final String NEXT_SCRIPT_FORMAT = 
			//"#{facesContext.validationFailed ? 'say();' : 'say();'}";
			//"if (args &amp;&amp; args.validationFailed) alert('error') else %s";
			//"if (args &amp;&amp; args.validationFailed) say();";
			"if(#{not facesContext.validationFailed}) myWizard.next();";
	*/
	
	private String widgetVar;
	private AbstractFormSubmitAction<OBJECT> previous,next,submitAction;
	private Integer currentStepIndex=0;
	private String[] stepIds;
	
	public WizardHelper(String[] stepIds,AbstractFormSubmitAction<OBJECT> submitAction,OBJECT object,MessageManager messageManager,ConstantResources constantResources) {
		this.widgetVar = constantResources.getWidgetVarWizard();
		this.stepIds = stepIds;
		this.submitAction = submitAction;
		this.submitAction.setRendered(isShowSubmitAction());
		previous = new AbstractFormSubmitAction<OBJECT>(object,messageManager,"boutton.previous","ui-icon-arraowthick-1-w",null,Boolean.TRUE,Boolean.TRUE,NavigationManager.OUTCOME_CURRENT_VIEW) {
			private static final long serialVersionUID = -2683422739395829063L;
			@Override protected void action() {
				previous();
			}
		};
		config(previous, "previous();");
		next = new AbstractFormSubmitAction<OBJECT>(object,messageManager,"boutton.next","ui-icon-arraowthick-1-e",null,Boolean.TRUE,Boolean.TRUE,NavigationManager.OUTCOME_CURRENT_VIEW) {
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
		System.out.println("WizardHelper.previous()");
		currentStepIndex--;
		submitAction.setRendered(isShowSubmitAction());
	}
	
	protected void next(){
		System.out.println("WizardHelper.next()");
		currentStepIndex++;
		submitAction.setRendered(isShowSubmitAction());
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
