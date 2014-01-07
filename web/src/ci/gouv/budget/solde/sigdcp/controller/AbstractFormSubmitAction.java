package ci.gouv.budget.solde.sigdcp.controller;

import java.io.Serializable;

import javax.faces.context.FacesContext;

import lombok.Getter;
import lombok.Setter;

import org.apache.commons.lang3.StringUtils;
import org.primefaces.component.commandbutton.CommandButton;

import ci.gouv.budget.solde.sigdcp.service.utils.validaton.AbstractValidator;

/**
 * Action de traitement d'un formulaire
 */
public abstract class AbstractFormSubmitAction<DTO> extends CommandButton implements Serializable {

	private static final long serialVersionUID = 3873845367443589081L;
	
	protected AbstractFormUIController<DTO> form;
	
	@Getter @Setter
	protected String successOutcome,notificationMessageId;
	@Getter @Setter
	protected Integer executionCount = 0;
	
	@Setter
	protected AbstractValidator<DTO> validator;
	
	public AbstractFormSubmitAction(AbstractFormUIController<DTO> form,String labelId, String icon,String notificationMessageId ,Boolean ajax,Boolean rendered,String successOutcome) {
		super();
		this.form = form;
		this.setValue(form.getMessageManager().getTextService().find(labelId));
		this.setIcon(icon);
		this.setAjax(ajax);
		this.setRendered(rendered);
		this.notificationMessageId = notificationMessageId;
		this.successOutcome = successOutcome;
	}
	
	public AbstractFormSubmitAction(AbstractFormUIController<DTO> form,String labelId, String icon,String notificationMessageId ,Boolean ajax,Boolean rendered) {
		this(form,labelId,icon,notificationMessageId,ajax,rendered,NavigationManager.OUTCOME_SUCCESS_VIEW);
	}
	
	public final String execute(){
		if(valide()){
			try {
				action();
				executionCount++;
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
	
	protected abstract void action() throws Exception;
	
	/**
	 * Validation des données ( fournies par l'utilisateur )
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected Boolean valide(){
		if(form.getDto() == null)
			return Boolean.TRUE;
		if(validator==null)
			validator = (AbstractValidator<DTO>) new AbstractValidator<>(form.getDto().getClass());
	
		validator.validate(form.getDto());
		for(String m : validator.getMessages()){
			form.getMessageManager().addError(m,Boolean.FALSE);
			System.out.println(m);
		}
		if(validator.isSucces())
			;
		else{
			FacesContext.getCurrentInstance().validationFailed();
		}
		return validator.isSucces();
	}
	
	private String echec(){
		return null;
	}
	
	protected String notificationMessage(){
		if(StringUtils.isNotEmpty(notificationMessageId))
			return form.getMessageManager().getTextService().find(notificationMessageId);
		return null;
	}
	
	public Boolean isExecutedAtLeastOnce(){
		return executionCount > 0;
	}


	
}
