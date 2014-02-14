package ci.gouv.budget.solde.sigdcp.controller.ui.form.command;

import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedList;
import java.util.logging.Level;

import javax.faces.context.FacesContext;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;

import org.apache.commons.lang3.StringUtils;
import org.primefaces.component.commandbutton.CommandButton;

import ci.gouv.budget.solde.sigdcp.controller.NavigationManager;
import ci.gouv.budget.solde.sigdcp.controller.ui.form.AbstractFormUIController;
import ci.gouv.budget.solde.sigdcp.service.utils.validaton.ObjectValidator;

/**
 * Commande d'un formulaire
 */
@Log
public class FormCommand<DTO> extends CommandButton implements Serializable {

	private static final long serialVersionUID = 3873845367443589081L;
	
	protected AbstractFormUIController<DTO> form;
	
	@Getter @Setter
	protected String successOutcome=NavigationManager.OUTCOME_SUCCESS_VIEW,notificationMessageId;
	
	@Getter @Setter
	protected Action _action,_echec,_notificationMessage,_successOutcome;
	
	@Getter
	protected Collection<ObjectValidator<?>> objectValidators=new LinkedList<>();
	
	protected Boolean onSuccessStayOnSameView = Boolean.FALSE;
	
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
				
		if(valide()){
			try {
				_action.execute(object);
				if(Boolean.TRUE.equals(onSuccessStayOnSameView)){
					String message = notificationMessage();
					if(StringUtils.isNotEmpty(message))
						form.getMessageManager().addInfo(message,Boolean.FALSE);
				}
			} catch (Exception e) {
				
					form.getMessageManager().addError(e);
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
	

	/*-----------------------------------------------------------------------------------------------------------*/
	
	
	
}
