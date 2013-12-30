package ci.gouv.budget.solde.sigdcp.controller;

import java.io.Serializable;

import javax.inject.Inject;

import lombok.Getter;
import lombok.Setter;
import ci.gouv.budget.solde.sigdcp.controller.application.UserSessionManager;
import ci.gouv.budget.solde.sigdcp.service.resources.CRUDType;

@Getter
public abstract class AbstractFormUIController extends AbstractUIController implements Serializable {

	private static final long serialVersionUID = 3873845367443589081L;
	
	/**
	 * Paramètre de requête de URL (nom du paramètre est OPERATION)
	 */
	@Getter @Setter
	protected CRUDType crudType;
	
	/**
	 * button d'envoi des informations
	 */
	protected String actionLabel = i18n("boutton.envoyer");
	protected Boolean actionAjax = Boolean.TRUE;
	
	@Getter protected Boolean editable = Boolean.TRUE;
	@Inject protected UserSessionManager userSessionManager;
	@Getter protected Boolean closeable = Boolean.TRUE;
	
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
		return echec();
	}
	
	protected void action(){}
	
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
	
	public String close(){
		return  userSessionManager.isLoggedIn()?"index":"connexionForm";
	}
	
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
