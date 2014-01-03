package ci.gouv.budget.solde.sigdcp.controller.identification;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import ci.gouv.budget.solde.sigdcp.controller.AbstractFormSubmitAction;
import ci.gouv.budget.solde.sigdcp.controller.AbstractFormUIController;
import ci.gouv.budget.solde.sigdcp.controller.application.UserSessionManager;
import ci.gouv.budget.solde.sigdcp.model.identification.CompteUtilisateur;
import ci.gouv.budget.solde.sigdcp.service.identification.CompteUtilisateurService;

@Named @RequestScoped
public class LogoutController extends AbstractFormUIController<CompteUtilisateur> implements Serializable {
 
	private static final long serialVersionUID = 6591392098578555259L;
	
	@Inject private CompteUtilisateurService compteUtilisateurService;
	@Inject UserSessionManager userSessionManager;
	
	@PostConstruct
	private void postConstructorLogoutController() {
		defaultSubmitAction = new AbstractFormSubmitAction<CompteUtilisateur>(null,messageManager,"boutton.sedeconnecter","ui-icon-check","notification.compte.deconnecte",Boolean.FALSE,Boolean.TRUE) {
			private static final long serialVersionUID = -2683422739395829063L;
			@Override
			protected void action() {
				compteUtilisateurService.deconnecter(userSessionManager.getCompte());
				FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
			}
		};
	}	

}
