package ci.gouv.budget.solde.sigdcp.controller;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.annotation.PostConstruct;
import javax.faces.application.ConfigurableNavigationHandler;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.application.NavigationCase;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public abstract class AbstractUIController implements Serializable {

	private static final long serialVersionUID = 370026962329124294L;
	
	private static final ResourceBundle I18N = ResourceBundle.getBundle("ci.gouv.budget.solde.sigdcp.service.resources.i18n.message",Locale.FRENCH);
	
	protected FacesContext facesContext;
	protected ConfigurableNavigationHandler navigationHandler;
	
	protected String title = "Titre de la vue";
	@Inject protected ConstantResources constantResources;
	
	public AbstractUIController() {
	    facesContext = FacesContext.getCurrentInstance();
	    navigationHandler = (ConfigurableNavigationHandler) facesContext.getApplication().getNavigationHandler();
	}
	
	@PostConstruct
	private void __postConsctruct__(){
		postConstruct();
	}
	
	protected void postConstruct(){}
	
	public void preRenderView(){
		// with JSF 2.2 use <f:action instead
		if (!FacesContext.getCurrentInstance().isPostback()) {
	        __firstPreRenderView__();
	    }
	}
	
	public void __firstPreRenderView__(){};
	
	/* useful methods */
	
	protected void addMessage(Severity severity,String message){
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severity,message, message));
	}
	
	protected void addMessageError(String message){
		addMessage(FacesMessage.SEVERITY_ERROR, message);
	}
	
	protected static String i18n(String id){
		return I18N.getString(id);
	}
	
	protected String outcome(String id,Object[] parameters){
		NavigationCase navigationCase = navigationHandler.getNavigationCase(facesContext, null, id);
		StringBuilder url = new StringBuilder(navigationCase.getToViewId(facesContext));
	    url.append("?faces-redirect="+navigationCase.isRedirect());
	    if(parameters!=null && parameters.length>0){
	    	for(int i=0;i<parameters.length-1;i++)
				try {
					url.append((i==0?"":"&")+parameters[i]+"="+URLEncoder.encode(parameters[i+1].toString(), "UTF-8"));
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
	    }
		return url.toString();
	}
	
	protected String outcome(String id){
		return outcome(id, null);
	}
	
	protected String addQueryParameters(String url,Object[] parameters){
		StringBuilder params = new StringBuilder();
		boolean hasQueryMark = url.contains("?");
	    if(parameters!=null && parameters.length>0){
	    	if(!hasQueryMark)
	    		params.append("?");
	    	for(int i=0;i<parameters.length-1;i++)
				try {
					params.append((hasQueryMark?"&":"?")+parameters[i]+"="+URLEncoder.encode(parameters[i+1].toString(), "UTF-8"));
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
	    }
	    return url+params.toString();
	}
}
