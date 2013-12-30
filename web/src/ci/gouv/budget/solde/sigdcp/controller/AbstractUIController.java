package ci.gouv.budget.solde.sigdcp.controller;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public abstract class AbstractUIController implements Serializable {

	private static final long serialVersionUID = 370026962329124294L;
	

	
	protected String title = "Titre du formulaire";
	@Inject protected ConstantResources constantResources;
	
	protected void addMessage(Severity severity,String message){
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severity,message, message));
	}
	
	protected void addMessageError(String message){
		addMessage(FacesMessage.SEVERITY_ERROR, message);
	}
	
	@PostConstruct
	private void __postConsctruct__(){
		postConstruct();
	}
	
	protected void postConstruct(){}

}
