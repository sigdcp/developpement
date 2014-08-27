package ci.gouv.budget.solde.sigdcp.controller.ui.form.command;

import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedList;
import java.util.logging.Level;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.primefaces.component.commandbutton.CommandButton;

import ci.gouv.budget.solde.sigdcp.controller.NavigationManager;
import ci.gouv.budget.solde.sigdcp.controller.WebConstantResources;
import ci.gouv.budget.solde.sigdcp.controller.ui.form.AbstractFormUIController;
import ci.gouv.budget.solde.sigdcp.service.AbstractServiceException;
import ci.gouv.budget.solde.sigdcp.service.ServiceException;
import ci.gouv.budget.solde.sigdcp.service.utils.validaton.ObjectValidator;

/**
 * Commande d'un formulaire
 */
@Log
public class FormCommand<DTO> extends CommandButton implements Serializable {

	private static final long serialVersionUID = 3873845367443589081L;
	
	@Getter
	protected AbstractFormUIController<DTO> form;
	
	@Getter @Setter
	protected String successOutcome=NavigationManager.OUTCOME_SUCCESS_VIEW,notificationMessageId;
	
	@Getter @Setter
	protected Action _action,_echec,_notificationMessage,_successOutcome,_actionListener,_dialogReturn,_beforeValide;
	
	@Getter
	protected Collection<ObjectValidator<?>> objectValidators=new LinkedList<>();
	
	protected Boolean onSuccessStayOnSameView = Boolean.FALSE;
	
	@Getter
	protected Boolean onlyActionListener=Boolean.FALSE;
	
	public FormCommand(AbstractFormUIController<DTO> form) {
		this.form = form;
		setUpdate("@form");
		setProcess("@form");
	}
	
	public FormCommand<DTO> init(String labelId, String icon,String notificationMessageId,Action _action) {
		this.setValue(form.getMessageManager().getTextService().find(labelId));
		this.setIcon(icon);
		this.notificationMessageId = notificationMessageId;
		this._action = _action;
		objectValidators.add(new ObjectValidator<Object>(form));//we validate the form by default
		return this;
	}
	
	public final String execute(Object object){
		if(_beforeValide!=null)
			try {
				_beforeValide.execute(null);
			} catch (Exception e1) {
				e1.printStackTrace();
			}		
		if(valide()){
			try {
				_action.execute(object);
				if(Boolean.TRUE.equals(onSuccessStayOnSameView)){
					String message = notificationMessage();
					if(StringUtils.isNotEmpty(message))
						form.getMessageManager().addInfo(message,Boolean.FALSE);
					
					return NavigationManager.OUTCOME_CURRENT_VIEW;
				}
			} catch (Exception e) {
				//e.printStackTrace();
				Throwable cause = null;
				Throwable index = e;
				while(index.getCause()!=null){
					if(index.getCause() instanceof ServiceException){
						cause = index.getCause();
						break;
					}else
						index = index.getCause();
				}
				if(cause==null)
					cause = e;
				
				if(cause instanceof AbstractServiceException)
					form.getMessageManager().addError(cause);
				else{
					form.getMessageManager().addError("Une erreur est servenue lors de l'execution de l'operation."
							+ "Veuillez contactez l'administrateur du système",false);
					cause.printStackTrace();
					
				}
				
				return echec(e);
			}
			//System.out.println("OUTCOME : "+successOutcome);
			//return successOutcome;
			//System.out.println(form.getNavigationManager().url(successOutcome,new Object[]{"id",12},true));
			return successOutcome();//+"?myid=1";
		}
		return echec(null);
	}
	
	public final String execute(){
		return execute(null);
	}

	/**
	 * Validation des données ( fournies par l'utilisateur )
	 * @return
	 */
	protected Boolean valide(){
		
		Boolean succeed = Boolean.TRUE;
		for(ObjectValidator<?> objectValidator : objectValidators){
			if(!objectValidator.validate()){
				succeed = Boolean.FALSE;
				for(String m : objectValidator.getValidator().getMessages()){
					form.getMessageManager().addError(m,Boolean.FALSE);
				}
			}
		}
		
		if(succeed)
			;
		else{
			FacesContext.getCurrentInstance().validationFailed();
		}
		return succeed;
	}

	private String echec(Exception exception){
		if(_echec==null)
			return null;
		try {
			return (String) _echec.__execute__(exception);
		} catch (Exception e) {
			log.log(Level.SEVERE, e.toString(), e);
			return null;
		}
	}
	
	protected String notificationMessage(){
		if(_notificationMessage==null)
			if(StringUtils.isNotEmpty(notificationMessageId))
				return form.getMessageManager().getTextService().find(notificationMessageId);
			else 
				return null;
		try {
			return (String) _notificationMessage.execute(null);
		} catch (Exception e) {
			log.log(Level.SEVERE, e.toString(), e);
			return null;
		}
	}
	
	protected String successOutcome(){
		if(_successOutcome==null)
			return successOutcome;
		try {
			return (String) _successOutcome.execute(null);
		} catch (Exception e) {
			log.log(Level.SEVERE, e.toString(), e);
			return null;
		}
	}
	
	public FormCommand<DTO> onSuccessStayOnCurrentView(){
		successOutcome = NavigationManager.OUTCOME_CURRENT_VIEW;
		return this;
	}
	
	public void onSuccessGoBack(final String messageId,final Object[] params){
		onSuccessGoTo(form.getUrl(), messageId, params);
	}
	
	public void onSuccessGoTo(final String url,final String messageId,final Object[] params){
		_successOutcome = new Action() {
			private static final long serialVersionUID = -2130020867972050541L;
			@Override
			protected Object __execute__(Object object) throws Exception {
				WebConstantResources wc = form.getWebConstantResources();
				
				Object[] _params = ArrayUtils.addAll(params, wc.getRequestParamMessageId(),messageId,wc.getRequestParamUrl(),url,
						wc.getRequestParamPageTemplate(),form.getShowPageHeader()?wc.getRequestParamPageTemplateCommon():wc.getRequestParamPageTemplateDialog());
				
				return form.getNavigationManager().url(NavigationManager.OUTCOME_SUCCESS_VIEW,_params,true);
			}
		};
		
	}
	
	public void onSuccessGoBack(){
		onSuccessGoBack("",null);
	}
	
	public void actionListener(ActionEvent actionEvent){
		if(_actionListener!=null)
			try {
				_actionListener.execute(actionEvent);
			} catch (Exception e) {
				log.log(Level.SEVERE, e.toString(), e);
			}
	}
	
	public void dialogReturn(Object data){
		if(_dialogReturn!=null)
			try {
				_dialogReturn.execute(data);
			} catch (Exception e) {
				log.log(Level.SEVERE, e.toString(), e);
			}
	}
	

	/*-----------------------------------------------------------------------------------------------------------*/
	
	
	
}
