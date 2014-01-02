package ci.gouv.budget.solde.sigdcp.controller;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import ci.gouv.budget.solde.sigdcp.service.utils.validaton.AbstractValidator;

/**
 * Action de traitement d'un formulaire
 */
public abstract class AbstractFormSubmitAction<DTO>  implements Serializable {

	private static final long serialVersionUID = 3873845367443589081L;
	
	protected MessageManager messageManager;
	
	@Getter @Setter
	protected String label,icon="ui-icon-check",update="@form",successOutcome,
			notificationMessageId;
	@Getter @Setter
	protected Boolean ajax = Boolean.TRUE,rendered = Boolean.TRUE,immediate = Boolean.FALSE;
	@Getter @Setter
	protected Integer executionCount = 0;
	
	@Setter
	protected DTO dto;
	@Setter
	protected AbstractValidator<DTO> validator;
	
	public AbstractFormSubmitAction(DTO dto,MessageManager messageManager,String labelId, String icon,String notificationMessageId ,Boolean ajax,Boolean rendered,String successOutcome) {
		super();
		this.dto = dto;
		this.messageManager = messageManager;
		this.label = this.messageManager.getTextService().find(labelId);
		this.icon = icon;
		this.ajax = ajax;
		this.rendered = rendered;
		this.notificationMessageId = notificationMessageId;
		this.successOutcome = successOutcome;
	}
	
	public AbstractFormSubmitAction(DTO dto,MessageManager messageManager,String labelId, String icon,String notificationMessageId ,Boolean ajax,Boolean rendered) {
		this(dto,messageManager,labelId,icon,notificationMessageId,ajax,rendered,AbstractUIController.OUTCOME_SUCCESS_VIEW);
	}
	
	public final String execute(){
		if(valide()){
			try {
				action();
				executionCount++;
				messageManager.addInfo(notificationMessage(),Boolean.FALSE);
			} catch (Exception e) {
				messageManager.addError(e);
				return echec();
			}
			return successOutcome;
		}
		return echec();
	}
	
	protected abstract void action();
	
	/**
	 * Validation des données ( fournies par l'utilisateur )
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected Boolean valide(){
		if(validator==null)
			validator = (AbstractValidator<DTO>) new AbstractValidator<>(dto.getClass());
	
		validator.validate(dto);
		for(String m : validator.getMessages())
			messageManager.addError(m,Boolean.FALSE);
		return validator.isSucces();
	}
	
	private String echec(){
		return null;
	}
	
	protected String notificationMessage(){
		return messageManager.getTextService().find(notificationMessageId);
	}
	
	public Boolean isExecutedAtLeastOnce(){
		return executionCount > 0;
	}


	
}
