package ci.gouv.budget.solde.sigdcp.controller;

import java.io.Serializable;

import javax.inject.Inject;

import lombok.Getter;
import ci.gouv.budget.solde.sigdcp.controller.application.UserSessionManager;
import ci.gouv.budget.solde.sigdcp.service.utils.ServiceValidationUtils;
import ci.gouv.budget.solde.sigdcp.service.utils.validaton.AbstractValidator;

@Getter
public class AbstractUIFormController extends AbstractUIController implements Serializable {

	private static final long serialVersionUID = 3873845367443589081L;
	
	@Inject protected ServiceValidationUtils validationUtils;
	@Getter protected Boolean editable = Boolean.TRUE;
	@Inject protected UserSessionManager userSessionManager;
	
	@Getter protected Boolean closeable = Boolean.TRUE;
	
	protected AbstractValidator<?> validator;
	
	public String submit(){
		if(valide()){
			try {
				action();
			} catch (Exception e) {
				addMessageError(e.getMessage());
				return echec();
			}
			return succes();
		}
		//else
			//addMessageError(constantResources.getValuesRequiredMessage());
		return echec();
	}
	
	protected void action(){}
	
	/**
	 * Validation des données ( fournies par l'utilisateur )
	 * @return
	 */
	protected Boolean valide(){
		//if(validator==null)
			return Boolean.TRUE;
		//validator.validate(object);
	}
	
	/**
	 * 
	 * @return
	 */
	protected String succes(){
		return "succes";
	}
	
	/**
	 * 
	 * @return
	 */
	protected String echec(){
		return null;
	}
	
	public String close(){
		return  userSessionManager.isLoggedIn()?"index":"connexionForm";
	}
	
}
