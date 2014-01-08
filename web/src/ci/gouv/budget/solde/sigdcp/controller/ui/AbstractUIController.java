package ci.gouv.budget.solde.sigdcp.controller.ui;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import lombok.Getter;
import lombok.Setter;
import ci.gouv.budget.solde.sigdcp.controller.MessageManager;
import ci.gouv.budget.solde.sigdcp.controller.NavigationManager;
import ci.gouv.budget.solde.sigdcp.controller.WebConstantResources;
import ci.gouv.budget.solde.sigdcp.service.resources.ServiceConstantResources;


public abstract class AbstractUIController implements Serializable {

	private static final long serialVersionUID = 370026962329124294L;
	
	public enum InitWhen {POST_CONSTRUCT,FIRST_PRERENDER_VIEW}
	
	@Inject @Getter protected ServiceConstantResources constantResources;
	@Inject @Getter protected WebConstantResources webConstantResources;
	@Inject @Getter protected NavigationManager navigationManager;
	@Inject @Getter protected MessageManager messageManager;
	
	/*
	 * Attributes
	 */
	@Getter @Setter protected String title = "Titre de la vue",internalCode="FS_PS_NOM_ECRAN_NUMERO";
	@Getter @Setter protected Boolean showInternalCode = Boolean.TRUE;
	
	@PostConstruct
	private void __postConsctruct__(){
		//postConstruct();
		if(InitWhen.POST_CONSTRUCT.equals(initWhen()))
			initialisation();
	}
	
	//protected final void postConstruct(){}
	
	public void preRenderView(){
		// with JSF 2.2 use <f:action instead
		if (!FacesContext.getCurrentInstance().isPostback()) {
	        //__firstPreRenderView__();
	        if(InitWhen.FIRST_PRERENDER_VIEW.equals(initWhen()))
				initialisation();
	    }
	}
	
	//public final void __firstPreRenderView__(){};
	
	protected void initialisation(){}
	
	protected InitWhen initWhen(){
		return InitWhen.POST_CONSTRUCT;
	}
	
	/* useful methods */
	
	protected String text(String id){
		return messageManager.getTextService().find(id);
	}
}
