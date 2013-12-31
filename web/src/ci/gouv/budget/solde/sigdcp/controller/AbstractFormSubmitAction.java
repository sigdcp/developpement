package ci.gouv.budget.solde.sigdcp.controller;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import lombok.Getter;

@Getter
public abstract class AbstractFormSubmitAction  implements Serializable {

	private static final long serialVersionUID = 3873845367443589081L;
	
	/**
	 * button d'envoi des informations
	 */
	protected String actionLabel = "Envoyer";
	protected Boolean actionAjax = Boolean.TRUE;
	
	public AbstractFormSubmitAction(String actionLabel) {
		super();
		this.actionLabel = actionLabel;
	}
	
	public String execute(){
		if(valide()){
			try {
				action();
			} catch (Exception e) {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), e.getMessage()));
				return echec();
			}
			return succes();
		}
		return echec();
	}
	
	protected abstract void action();
	
	/**
	 * Validation des données ( fournies par l'utilisateur )
	 * @return
	 */
	protected Boolean valide(){
		return Boolean.TRUE;
	}
	
	protected String succes(){
		return "succes";
	}
	
	protected String echec(){
		return null;
	}

	
	

	
}
