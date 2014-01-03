package ci.gouv.budget.solde.sigdcp.controller;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import lombok.Getter;
import lombok.Setter;


public abstract class AbstractUIController implements Serializable {

	private static final long serialVersionUID = 370026962329124294L;
	
	/**
	 * We stay on the same view after action
	 */
	public static final String OUTCOME_CURRENT_VIEW = null;
	/**
	 * We move to the success view after action
	 */
	public static final String OUTCOME_SUCCESS_VIEW = "succes";
	
	//@Inject protected FacesContext facesContext;
	//@Inject protected ConfigurableNavigationHandler navigationHandler;
	@Inject protected ConstantResources constantResources;
	@Inject protected NavigationManager navigationManager;
	@Inject protected MessageManager messageManager;
	
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
	

}
