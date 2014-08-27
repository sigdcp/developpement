package ci.gouv.budget.solde.sigdcp.controller.ui.form;

import java.io.Serializable;

import javax.faces.event.ActionEvent;
import javax.inject.Inject;

import lombok.Getter;
import lombok.Setter;
import ci.gouv.budget.solde.sigdcp.controller.application.UserSessionManager;
import ci.gouv.budget.solde.sigdcp.controller.ui.AbstractUIController;
import ci.gouv.budget.solde.sigdcp.controller.ui.form.command.Action;
import ci.gouv.budget.solde.sigdcp.controller.ui.form.command.FormCommand;
import ci.gouv.budget.solde.sigdcp.service.AbstractServiceException;
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
	protected AbstractServiceException serviceException;
	
	@Override
	protected InitWhen initWhen() {
		//because view might come with parameters
		return InitWhen.FIRST_PRERENDER_VIEW;
	}
	
	@Override
	protected void initialisation() {
		super.initialisation();
		try {
			initDto();
		} catch (AbstractServiceException e) {
			serviceException = e;
			initDtoException(e);
			return;
		}
		
		initCommands();
		afterDtoInit();
		showFieldRequired = isEditable();
		requiredEnabled=isEditable();
	}
	
	protected void initDto(){
		if(isCreate())
			initCreateOperation();
		else if(isRead())
			initReadOperation();	
	}
	
	protected void initDtoException(AbstractServiceException exception){
		crudType = CRUDType.READ;
		redirectUrl(navigationManager.url("succes",new Object[]{webConstantResources.getRequestParamRuntimeMessageId(),runtimeMessageId(exception.getMessage())},false,false));
		return;
	}
	
	protected void afterDtoInit(){}
	
	protected void initCreateOperation(){
		
	}
	
	protected void initReadOperation(){
		
	}
	
	public FormCommand<DTO> createCommand(){
		FormCommand<DTO> c = new FormCommand<DTO>(this);
		commonCommandConfig(c);
		return c;
	}
	
	protected void commonCommandConfig(FormCommand<DTO> command){
		command.setOnclick("formSubmitOnstart();");
		command.setOnstart("formSubmitOnstart();");
		command.setOncomplete("formSubmitOncomplete(xhr, status, args);");
		command.setOnerror("formSubmitOnerror();");
	}
	
	protected void initCommands(){
		defaultSubmitCommand = createCommand().init("bouton.enregistrer","ui-icon-check",null, new Action() {
			private static final long serialVersionUID = 1L;
			@Override
			protected Object __execute__(Object object) throws Exception {
				onDefaultSubmitAction();
				return null;
			}
		});
		defaultSubmitCommand.set_actionListener(new Action() {
			private static final long serialVersionUID = -671883400702777924L;
			@Override
			protected Object __execute__(Object object) throws Exception {
				onDefaultSubmitActionListener((ActionEvent) object);
				return null;
			}
		});
		
		commonCommandConfig(defaultSubmitCommand);
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
		closeCommand.setType("button");

		warnOnClosing(isEditable());
		//closeCommand.setSuccessOutcome(null);
		closeCommand.setAjax(Boolean.FALSE);
		
		//closeCommand.setSuccessOutcome(userSessionManager.isLoggedIn()?"espacePrivee":"index");
		closeCommand.setImmediate(Boolean.TRUE);
		closeCommand.setProcess("@this");
	}
	
	public DTO getDto(){
		return null;
	}
	
	protected void onDefaultSubmitAction() throws Exception {}
	
	protected void onDefaultSubmitActionListener(ActionEvent actionEvent) throws Exception {}
	
	public void onDialogReturn(){}
	
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
	
	protected void warnOnClosing(Boolean warn){
		if(warn==null)
			warn = Boolean.FALSE;
		closeCommand.setOnclick("return quitter("+warn+");");
		
	}
	
	public String getCssColumnClassesPanelGrid(){
		return isEditable()?"c1,c2,c3,c1,c2,c3":"c1,c2,c1,c2";
	}
	
	public int getColumnCountPanelGrid(){
		return isEditable()?6:4;
	}
	
}
