package ci.gouv.budget.solde.sigdcp.controller.ui.form.command;

import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedList;

import javax.faces.context.FacesContext;

import lombok.Getter;
import lombok.Setter;

import org.apache.commons.lang3.StringUtils;
import org.primefaces.component.commandbutton.CommandButton;

import ci.gouv.budget.solde.sigdcp.controller.NavigationManager;
import ci.gouv.budget.solde.sigdcp.controller.ui.form.AbstractFormUIController;
import ci.gouv.budget.solde.sigdcp.service.utils.validaton.ObjectValidator;

/**
 * Commande d'un formulaire
 */
public class FormCommand<DTO> extends CommandButton implements Serializable {

	private static final long serialVersionUID = 3873845367443589081L;
	
	protected AbstractFormUIController<DTO> form;
	
	@Getter @Setter
	protected String successOutcome=NavigationManager.OUTCOME_SUCCESS_VIEW,notificationMessageId;
	
	@Getter @Setter
	protected Action _action;
	
	@Getter
	protected Collection<ObjectValidator<?>> objectValidators=new LinkedList<>();
	
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
				String message = notificationMessage();
				if(StringUtils.isNotEmpty(message))
					form.getMessageManager().addInfo(message,Boolean.FALSE);
			} catch (Exception e) {
				form.getMessageManager().addError(e);
				return echec();
			}
			//System.out.println("OUTCOME : "+successOutcome);
			return successOutcome;
		}
		return echec();
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

	private String echec(){
		return null;
	}
	
	protected String notificationMessage(){
		if(StringUtils.isNotEmpty(notificationMessageId))
			return form.getMessageManager().getTextService().find(notificationMessageId);
		return null;
	}
	
	public FormCommand<DTO> onSuccessStayOnCurrentView(){
		successOutcome = NavigationManager.OUTCOME_CURRENT_VIEW;
		return this;
	}
	

	/*-----------------------------------------------------------------------------------------------------------*/
	
	
	
}
