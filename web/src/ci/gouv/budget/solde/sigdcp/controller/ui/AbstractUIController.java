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
import ci.gouv.budget.solde.sigdcp.service.utils.ServiceUtils;


public abstract class AbstractUIController implements Serializable {

	private static final long serialVersionUID = 370026962329124294L;
	
	public enum InitWhen {POST_CONSTRUCT,FIRST_PRERENDER_VIEW}
	
	@Inject @Getter protected ServiceConstantResources constantResources;
	@Inject @Getter protected WebConstantResources webConstantResources;
	@Inject @Getter protected NavigationManager navigationManager;
	@Inject @Getter protected MessageManager messageManager;
	@Inject @Getter protected ServiceUtils serviceUtils;
	
	@Getter @Setter protected String previousPath,viewType;
	
	/*
	 * Attributes
	 */
	@Getter @Setter protected String title = "Titre de la vue",internalCode="FS_PS_NOM_ECRAN_NUMERO";
	@Getter @Setter protected Boolean showInternalCode = Boolean.TRUE,validationFailed=Boolean.FALSE;
	
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
		//validationFailed = FacesContext.getCurrentInstance().isValidationFailed();
		initialisation();
		//previousPath = 
	}
	
	protected void initialisation(){}
	
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
}
