package ci.gouv.budget.solde.sigdcp.controller;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.logging.Level;

import javax.faces.application.ConfigurableNavigationHandler;
import javax.faces.application.NavigationCase;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import lombok.extern.java.Log;

import org.apache.commons.lang3.StringUtils;

@Log
public class NavigationManager implements Serializable {
	
	private static final long serialVersionUID = 4432678991321751425L;

	/**
	 * We stay on the same view after action
	 */
	public static final String OUTCOME_CURRENT_VIEW = null;
	/**
	 * We move to the success view after action
	 */
	public static final String OUTCOME_SUCCESS_VIEW = "succes";
	
	private static final String QUERY_START = "?";
	private static final String QUERY_SEPARATOR = "&";
	private static final String QUERY_NAME_VALUE_SEPARATOR = "=";
	private static final String QUERY_PARAMETER_ENCODING = "UTF-8";
	private static final String QUERY_PARAMETER_FACES_REDIRECT_NAME = "faces-redirect";
	private static final String FILE_STATIC_EXTENSION = ".xhtml";
	private static final String FILE_PROCESSING_EXTENSION = ".jsf";
		
	public String url(/*FacesContext facesContext,*/String id,Object[] parameters,Boolean actionOutcome){
		FacesContext facesContext = FacesContext.getCurrentInstance();
		NavigationCase navigationCase = ((ConfigurableNavigationHandler)facesContext.getApplication().getNavigationHandler()).getNavigationCase(facesContext, null, id);
		//System.out.println(id+" / "+navigationCase);
		String s = navigationCase.getToViewId(facesContext);
		StringBuilder url;
		if(Boolean.TRUE.equals(actionOutcome))
			url = new StringBuilder(s);
		else
			url = new StringBuilder(StringUtils.replace(s, FILE_STATIC_EXTENSION, FILE_PROCESSING_EXTENSION));
	    
		if(Boolean.TRUE.equals(actionOutcome))
	    	addParameter(url, QUERY_PARAMETER_FACES_REDIRECT_NAME, navigationCase.isRedirect());
	    if(parameters!=null && parameters.length>0){
	    	for(int i=0;i<parameters.length-1;i=i+2)
				addParameter(url, (String) parameters[i], parameters[i+1]);
	    }
		return url.toString();
	}
	
	public String url(/*FacesContext facesContext,*/String id,Object[] parameters){
		return url(/*facesContext,*/id, parameters, Boolean.TRUE);
	}
	
	public String url(/*FacesContext facesContext,*/String id,Boolean actionOutcome){
		return url(/*facesContext,*/id, null,actionOutcome);
	}
	
	public String url(/*FacesContext facesContext,*/String id){
		return url(/*facesContext,*/id, Boolean.TRUE);
	}
	
	public String addQueryParameters(String aUrl,Object[] parameters){
		System.out.println(aUrl);
		StringBuilder url = new StringBuilder(aUrl);
	    if(parameters!=null && parameters.length>0){
	    	for(int i=0;i<parameters.length-1;i=i+2)
	    		addParameter(url, (String) parameters[i], parameters[i+1]);
	    }
	    return url.toString();
	}
	
	private void addParameter(StringBuilder url,String name,Object value){
		try {
			url.append((url.toString().contains(QUERY_START)?QUERY_SEPARATOR:QUERY_START)+name+QUERY_NAME_VALUE_SEPARATOR+URLEncoder.encode(value.toString(), QUERY_PARAMETER_ENCODING));
		} catch (UnsupportedEncodingException e) {
			log.log(Level.SEVERE,e.toString(),e);
		}
	}
	
	public String getRequestUrl(){
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		//System.out.println(request.getQueryString());
		//TODO not all queries got
		String url = request.getRequestURL().toString();
		if(StringUtils.isNotEmpty(request.getQueryString()))
			url += QUERY_START+request.getQueryString();
		return url;
	}
	

}
