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
import ci.gouv.budget.solde.sigdcp.service.utils.validaton.AbstractValidator;

/**
 * Commande d'un formulaire
 */
public class FormCommandOLD<DTO> extends CommandButton implements Serializable {

	private static final long serialVersionUID = 3873845367443589081L;
	
	protected AbstractFormUIController<DTO> form;
	
	@Getter @Setter
	protected String successOutcome=NavigationManager.OUTCOME_SUCCESS_VIEW,notificationMessageId;
	
	@Setter
	protected AbstractValidator<DTO> validator;
	@Getter @Setter
	protected Action _action;
	
	protected Collection<Object[]> extraValidators=new LinkedList<>();
	
	public FormCommandOLD(AbstractFormUIController<DTO> form) {
		this.form = form;
		setUpdate("@form");
		setProcess("@form");
	}
	/*
	public FormCommand(AbstractFormUIController<DTO> form,String labelId, String icon,String notificationMessageId ,String successOutcome,Action _action) {
		this.form = form;
		init(labelId, icon, notificationMessageId, successOutcome, _action);
	}
	
	public FormCommand(AbstractFormUIController<DTO> form,String labelId, String icon,String notificationMessageId,Action _action) {
		this.form = form;
		init(labelId,icon,notificationMessageId,NavigationManager.OUTCOME_SUCCESS_VIEW,_action);
	}
	*/
	public FormCommandOLD<DTO> init(String labelId, String icon,String notificationMessageId,Action _action) {
		this.setValue(form.getMessageManager().getTextService().find(labelId));
		this.setIcon(icon);
		this.notificationMessageId = notificationMessageId;
		this._action = _action;
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
				e.printStackTrace();
				form.getMessageManager().addError(e);
				return echec();
			}
			return successOutcome;
		}
		return echec();
	}
	
	public final String execute(){
		return execute(null);
	}
	/*
	protected abstract void action() throws Exception;
	*/
	/**
	 * Validation des données ( fournies par l'utilisateur )
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected Boolean valide(){
		Boolean succeed = Boolean.TRUE;
		if(form.getDto() != null){
			if(validator==null)
				validator = (AbstractValidator<DTO>) new AbstractValidator<>(form.getDto().getClass());
			//validator.validate(form.getDto());
			if(!validate((AbstractValidator<Object>) validator, form.getDto()))
				succeed = Boolean.FALSE;
		}
		
		//System.out.println("FormCommand.valide() : "+extraValidators);
		for(Object[] object : extraValidators){
			//AbstractValidator<Object> v = ;
			//Object bean = ;
			if(!validate((AbstractValidator<Object>) object[0], object[1]))
				succeed = Boolean.FALSE;
			/*
			v.validate(bean);
			*/
		}
		
		if(succeed)
			;
		else{
			FacesContext.getCurrentInstance().validationFailed();
		}
		return succeed;
	}
	
	private Boolean validate(AbstractValidator<Object> validator,Object bean){
		//validator.validate(bean);
		for(String m : validator.getMessages()){
			form.getMessageManager().addError(m,Boolean.FALSE);
			//System.out.println(m);
		}
		return validator.isSucces();
	}
	/*
	protected Collection<Object[]> extraValidators(){
		return null;
	}
	*/
	private String echec(){
		return null;
	}
	
	protected String notificationMessage(){
		if(StringUtils.isNotEmpty(notificationMessageId))
			return form.getMessageManager().getTextService().find(notificationMessageId);
		return null;
	}
	
	public FormCommandOLD<DTO> onSuccessStayOnCurrentView(){
		successOutcome = NavigationManager.OUTCOME_CURRENT_VIEW;
		return this;
	}
	

	/*-----------------------------------------------------------------------------------------------------------*/
	
	public void addExtraValidator(AbstractValidator<?> validator,Object object){
		extraValidators.add(new Object[]{validator,object});
	}
	
	public void addExtraValidatorFor(Object object){
		addExtraValidator(new AbstractValidator<>(object.getClass()), object);
	}
	
}
