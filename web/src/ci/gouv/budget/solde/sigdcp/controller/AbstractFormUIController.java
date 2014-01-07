package ci.gouv.budget.solde.sigdcp.controller;

import java.io.Serializable;

import javax.inject.Inject;

import lombok.Getter;
import lombok.Setter;
import ci.gouv.budget.solde.sigdcp.controller.application.UserSessionManager;
import ci.gouv.budget.solde.sigdcp.service.resources.CRUDType;

@Getter
public abstract class AbstractFormUIController<OBJECT> extends AbstractUIController implements Serializable {

	private static final long serialVersionUID = 3873845367443589081L;
	
	/**
	 * Paramètre de requête de URL (nom du paramètre est OPERATION)
	 */
	@Getter @Setter
	protected CRUDType crudType;
	
	/**
	 * button par défaut d'envoi des informations
	 */
	protected AbstractFormSubmitAction<OBJECT> defaultSubmitAction;
	protected AbstractFormSubmitAction<OBJECT> closeAction;
	@Getter protected WizardHelper<OBJECT> wizardHelper;
	@Inject protected UserSessionManager userSessionManager;
	protected Boolean showFieldRequired = Boolean.TRUE;
	
	@Override
	public void __firstPreRenderView__() {
		super.__firstPreRenderView__();
		if(isCreate())
			initCreateOperation();
		else if(isRead())
			initReadOperation();	
		initActions();
	}
	
	protected void initCreateOperation(){
		
	}
	
	protected void initReadOperation(){
		
	}
	
	protected void initActions(){
		defaultSubmitAction = new AbstractFormSubmitAction<OBJECT>(this,"bouton.envoyer","ui-icon-check",null,Boolean.TRUE,Boolean.TRUE) {
			private static final long serialVersionUID = -2683422739395829063L;
			@Override
			protected void action() {
				onDefaultSubmitAction();
			}
		};
		
		closeAction = new AbstractFormSubmitAction<OBJECT>(this,"bouton.fermer","ui-icon-close",null,Boolean.TRUE,Boolean.TRUE,userSessionManager.isLoggedIn()?"espacePrivee":"index") {
			private static final long serialVersionUID = -2683422739395829063L;
			@Override
			protected void action() {
				onCloseAction();
			}
		};
		closeAction.setProcess("@this");
	}
	
	public OBJECT getDto(){
		return null;
	}
	
	protected void onDefaultSubmitAction(){}
	
	protected void onCloseAction(){}
	
	public boolean isCreate(){
		return CRUDType.CREATE.equals(crudType);
	}
	
	public boolean isUpdate(){
		return CRUDType.UPDATE.equals(crudType);
	}
	
	public boolean isRead(){
		return CRUDType.READ.equals(crudType);
	}
	
	public boolean isDelete(){
		return CRUDType.DELETE.equals(crudType);
	}
	
	public boolean isEditable(){
		return isCreate() || isUpdate();
	}
	
}
