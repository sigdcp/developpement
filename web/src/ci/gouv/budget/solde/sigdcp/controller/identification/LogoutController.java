package ci.gouv.budget.solde.sigdcp.controller.identification;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import ci.gouv.budget.solde.sigdcp.controller.AbstractUIFormController;
import ci.gouv.budget.solde.sigdcp.controller.application.UserSessionManager;
import ci.gouv.budget.solde.sigdcp.service.identification.CompteUtilisateurService;

@Named @RequestScoped
public class LogoutController extends AbstractUIFormController implements Serializable {
 
	private static final long serialVersionUID = 6591392098578555259L;
	
	@Inject private CompteUtilisateurService compteUtilisateurService;
	@Inject UserSessionManager userSessionManager;
		
	@Override
	protected void action() {
		compteUtilisateurService.deconnecter(userSessionManager.getCompte());
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
	}
		
	@Override
	protected String succes() {
		return "connexionForm";
	}
	

}
