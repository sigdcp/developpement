package ci.gouv.budget.solde.sigdcp.controller;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Singleton;

import lombok.Getter;

import org.apache.commons.lang3.StringUtils;

import ci.gouv.budget.solde.sigdcp.service.utils.TextService;

@Singleton
public class MessageManager implements Serializable {
	
	private static final long serialVersionUID = -2096649010369789825L;
	
	@Getter @Inject private TextService textService;
	 
	public void add(Severity severity,String text,Boolean isMessageId){
		String message = isMessageId?textService.find(text):text;
		message = StringUtils.replace(message, "\r\n", "<br/>");
		message = StringUtils.replace(message, "\n", "<br/>");
		//message = StringEscapeUtils.escapeHtml4(message);
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severity,message, message));
		if(FacesMessage.SEVERITY_ERROR.equals(severity))
			FacesContext.getCurrentInstance().validationFailed();
	}
	
	public void add(Severity severity,String messageId){
		add(severity, messageId, Boolean.TRUE);
	}
	
	public void addError(String text,Boolean isMessageId){
		add(FacesMessage.SEVERITY_ERROR, text,isMessageId);
	}
	
	public void addError(String text){
		addError(text, Boolean.TRUE);
	}
	
	public void addError(Throwable throwable){
		add(FacesMessage.SEVERITY_ERROR, StringUtils.isEmpty(throwable.getMessage())?throwable.toString():throwable.getMessage() ,Boolean.FALSE);
	}
	
	public void addInfo(String text,Boolean isMessageId){
		add(FacesMessage.SEVERITY_INFO, text,isMessageId);
	}
	
	public void addInfo(String messageId){
		addInfo(messageId, Boolean.TRUE);
	}
	
	

}
