package ci.gouv.budget.solde.sigdcp.controller.ui.form;

import java.io.Serializable;

import javax.inject.Inject;

import lombok.Getter;
import lombok.Setter;
import ci.gouv.budget.solde.sigdcp.controller.application.UserSessionManager;
import ci.gouv.budget.solde.sigdcp.controller.ui.AbstractUIController;
import ci.gouv.budget.solde.sigdcp.controller.ui.form.command.Action;
import ci.gouv.budget.solde.sigdcp.controller.ui.form.command.FormCommand;
import ci.gouv.budget.solde.sigdcp.service.resources.CRUDType;

@Getter
public abstract class AbstractFormUIController<DTO> extends AbstractUIController implements Serializable {

	private static final long serialVersionUID = 3873845367443589081L;
	
	/**
	 * Paramètre de requête de URL (nom du paramètre est OPERATION)
	 */
	@Setter
	protected CRUDType crudType;
	
	/**
	 * button par défaut d'envoi des informations
	 */
	protected FormCommand<DTO> defaultSubmitCommand,closeCommand;
	protected WizardHelper<DTO> wizardHelper;
	@Inject protected UserSessionManager userSessionManager;
	protected Boolean showFieldRequired,requiredEnabled=Boolean.TRUE;
	
	@Override
	protected InitWhen initWhen() {
		//because view might come with parameters
		return InitWhen.FIRST_PRERENDER_VIEW;
	}
	
	@Override
	protected void initialisation() {
		super.initialisation();
		showFieldRequired = isEditable();
		if(isCreate())
			initCreateOperation();
		else if(isRead())
			initReadOperation();	
		initCommands();
	}
	
	protected void initCreateOperation(){
		
	}
	
	protected void initReadOperation(){
		
	}
	
	public FormCommand<DTO> createCommand(){
		return new FormCommand<DTO>(this);
	}
	
	protected void initCommands(){
		defaultSubmitCommand = createCommand().init("bouton.envoyer","ui-icon-check",null, new Action() {
			private static final long serialVersionUID = 1L;
			@Override
			protected Object __execute__(Object object) throws Exception {
				onDefaultSubmitAction();
				return null;
			}
		});
		defaultSubmitCommand.setOnclick("formSubmitOnstart();");
		defaultSubmitCommand.setOnstart("formSubmitOnstart();");
		defaultSubmitCommand.setOncomplete("formSubmitOncomplete();");
		defaultSubmitCommand.setRendered(!isRead());
		
		closeCommand = createCommand().init("bouton.annuler","ui-icon-close",null, new Action() {
			private static final long serialVersionUID = 1L;
			@Override
			protected Object __execute__(Object object) throws Exception {
				onCloseAction();
				return null;
			}
		});
		closeCommand.setRendered(!isDialog());
		closeCommand.setOnclick("return quitter();");
		closeCommand.setSuccessOutcome(null);
		closeCommand.setAjax(Boolean.FALSE);
		//closeCommand.setSuccessOutcome(userSessionManager.isLoggedIn()?"espacePrivee":"index");
		closeCommand.setImmediate(Boolean.TRUE);
		closeCommand.setProcess("@this");
	}
	
	public DTO getDto(){
		return null;
	}
	
	protected void onDefaultSubmitAction() throws Exception {}
	
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
