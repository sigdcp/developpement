package ci.gouv.budget.solde.sigdcp.controller.ui;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.PostConstruct;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Inject;

import lombok.Getter;
import lombok.Setter;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.omnifaces.util.Faces;

import ci.gouv.budget.solde.sigdcp.controller.MessageManager;
import ci.gouv.budget.solde.sigdcp.controller.NavigationManager;
import ci.gouv.budget.solde.sigdcp.controller.WebConstantResources;
import ci.gouv.budget.solde.sigdcp.controller.application.RoleManager;
import ci.gouv.budget.solde.sigdcp.service.AbstractServiceException;
import ci.gouv.budget.solde.sigdcp.service.GenericService;
import ci.gouv.budget.solde.sigdcp.service.resources.ServiceConstantResources;
import ci.gouv.budget.solde.sigdcp.service.utils.NavigationHelper;
import ci.gouv.budget.solde.sigdcp.service.utils.ServiceUtils;


public abstract class AbstractUIController implements Serializable {

	private static final long serialVersionUID = 370026962329124294L;
	
	public enum InitWhen {POST_CONSTRUCT,FIRST_PRERENDER_VIEW}
	
	protected static Map<String,String> MESSAGES = new HashMap<>();
	
	/*
	 * Services
	 */
	@Inject protected GenericService genericService;
	
	@Inject @Getter transient protected ServiceConstantResources constantResources;
	@Inject @Getter transient protected WebConstantResources webConstantResources;
	@Inject transient protected RoleManager roleManager;
	@Inject @Getter transient protected NavigationManager navigationManager;
	@Inject transient protected NavigationHelper navigationHelper;
	@Inject @Getter transient protected MessageManager messageManager;
	@Inject @Getter transient protected ServiceUtils serviceUtils;
	
	@Getter @Setter protected String previousPath,url,viewType,autoComplete="on"/*,pageTemplate="/template/commun.xhtml"*/;
	
	@Getter protected Boolean showPageHeader=Boolean.TRUE,showPageFooter=Boolean.TRUE,showPageMenu=Boolean.TRUE;
	
	/*
	 * Attributes
	 */
	@Getter @Setter protected String title = "Titre de la vue",internalCode="FS_PS_NOM_ECRAN_NUMERO",instructions;
	@Getter @Setter protected Boolean showInternalCode = Boolean.FALSE,validationFailed=Boolean.FALSE,
			onServiceNotificationEventEnabled;
	
	@PostConstruct
	private void __postConsctruct__(){
		//postConstruct();
		if(InitWhen.POST_CONSTRUCT.equals(initWhen()))
			__initialisation__();
	}
	
	//protected final void postConstruct(){}
	
	public void preRenderView(){
		// with JSF 2.2 use <f:action instead
		if (!FacesContext.getCurrentInstance().isPostback()) {
	        //__firstPreRenderView__();
	        if(InitWhen.FIRST_PRERENDER_VIEW.equals(initWhen()))
	        	__initialisation__();
	    }
	}
	
	//public final void __firstPreRenderView__(){};
	
	private void __initialisation__(){
		url = navigationManager.getRequestUrl();
		viewType = Faces.getRequestParameter(webConstantResources.getRequestParamViewType());
		
		String pageTemplateId = Faces.getRequestParameter(webConstantResources.getRequestParamPageTemplate());
		if(webConstantResources.getRequestParamPageTemplateDialog().equals(pageTemplateId))
			showPageFooter = showPageHeader = showPageMenu = false ;
				
		//pageTemplate="/template/dialog.xhtml";
		//System.out.println(pageTemplate);
		//validationFailed = FacesContext.getCurrentInstance().isValidationFailed();
		initialisation();
		afterInitialisation();
		//previousPath = 
	}
	
	protected void initialisation(){}
	
	protected void afterInitialisation(){}
	
	protected InitWhen initWhen(){
		return InitWhen.POST_CONSTRUCT;
	}
	
	/* useful methods */
	
	protected String text(String id){
		return messageManager.getTextService().find(id);
	}
	
	protected String text(String id,Object[] parameters){
		return messageManager.getTextService().find(id,parameters);
	}
	
	protected Boolean isDialog(){
		return webConstantResources.getRequestParamDialog().equals(viewType);
	}
	/*
	public void onServiceNotificationEvent(@Observes(during = TransactionPhase.AFTER_COMPLETION) NotificationEvent notificationEvent) {
		if(Boolean.TRUE.equals(onServiceNotificationEventEnabled)){
			messageManager.addInfo(notificationEvent.getMessage().toString(), false);
		}
	}*/
	
	@SuppressWarnings("unchecked")
	protected <T> T attribute(UIComponent uiComponent,Class<T> type,String name){
		return (T) ((UIInput)uiComponent.getAttributes().get(name)).getValue();
	}
	
	protected <T> T attribute(ValueChangeEvent valueChangeEvent,Class<T> type){
		return attribute(valueChangeEvent.getComponent(), type, type.getClass().getSimpleName().toLowerCase());
	}
	
	protected String requestParameter(String name){
		return Faces.getRequestParameter(name);
	}
	
	protected Long requestParameterLong(String name){
		try {
			return Long.parseLong(requestParameter(name));
		} catch (NumberFormatException e) {
			return null;
		}
	}
	
	protected void redirectUrl(String url){
		try {
			Faces.redirect(url);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	protected String runtimeMessageId(String message){
		String id = MESSAGES.get(message);
		if(id==null){
			id = RandomStringUtils.randomAlphanumeric(4)+System.currentTimeMillis();
			MESSAGES.put(message, id);
		}
		return id;
	}
	
	protected String runtimeMessage(String id){
		for(Entry<String, String> entry : MESSAGES.entrySet())
			if(entry.getValue().equals(id))
				return entry.getKey();
		return null;
	}
	
	protected String runtimeMessageId(AbstractServiceException exception){
		return runtimeMessageId(exception.getMessage());
	}
	
	protected void runSimulation(long durationInMillis){
		try {
			Thread.sleep(durationInMillis);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	protected void debug(Object object){
		System.out.println("****************************** DEBUG STARTS ********************************");
		System.out.println(ToStringBuilder.reflectionToString(object, ToStringStyle.MULTI_LINE_STYLE));
		System.out.println("****************************** DEBUG ENDS ********************************");
	}
	
}
