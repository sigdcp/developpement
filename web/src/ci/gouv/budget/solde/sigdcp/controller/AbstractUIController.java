package ci.gouv.budget.solde.sigdcp.controller;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import lombok.Getter;
import lombok.Setter;
import ci.gouv.budget.solde.sigdcp.service.resources.ServiceConstantResources;


public abstract class AbstractUIController implements Serializable {

	private static final long serialVersionUID = 370026962329124294L;
		
	@Inject @Getter protected ServiceConstantResources constantResources;
	@Inject @Getter protected WebConstantResources webConstantResources;
	@Inject @Getter protected NavigationManager navigationManager;
	@Inject @Getter protected MessageManager messageManager;
	
	/*
	 * Attributes
	 */
	@Getter @Setter protected String title = "Titre de la vue";
	
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
	
	protected String text(String id){
		return messageManager.getTextService().find(id);
	}
}
