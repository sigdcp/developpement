package ci.gouv.budget.solde.sigdcp.controller.identification;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import lombok.Getter;
import lombok.Setter;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.web.util.SavedRequest;
import org.apache.shiro.web.util.WebUtils;
import org.omnifaces.util.Faces;

import ci.gouv.budget.solde.sigdcp.controller.application.UserSessionManager;
import ci.gouv.budget.solde.sigdcp.controller.ui.form.AbstractFormUIController;
import ci.gouv.budget.solde.sigdcp.controller.ui.form.command.Action;
import ci.gouv.budget.solde.sigdcp.model.identification.CompteUtilisateur;
import ci.gouv.budget.solde.sigdcp.model.identification.Credentials;
import ci.gouv.budget.solde.sigdcp.service.ServiceException;
import ci.gouv.budget.solde.sigdcp.service.ServiceExceptionType;
import ci.gouv.budget.solde.sigdcp.service.identification.CompteUtilisateurService;

@Named @RequestScoped
public class LoginController extends AbstractFormUIController<Credentials> implements Serializable {
 
	private static final long serialVersionUID = 6591392098578555259L;
	
	//public static final String HOME_URL = "private/espacePrive.jsf";
	
	/*
	 * Services
	 */
	@Inject private CompteUtilisateurService compteUtilisateurService;
	

	/*
	 * Dtos
	 */
	@Inject private UserSessionManager userSessionManager;
	@Getter private Credentials credentials = new Credentials();
	@Getter @Setter private Boolean remember = Boolean.FALSE;
	
	@Override
	protected InitWhen initWhen() {
		return InitWhen.POST_CONSTRUCT;
	}
	
	@Override
	protected void initialisation() {
		super.initialisation();
		defaultSubmitCommand.setValue("bouton.seconnecter");
		defaultSubmitCommand.set_echec(new Action() {
			private static final long serialVersionUID = -1763345724770503035L;
			@Override
			protected Object __execute__(Object object) throws Exception {
				if(object instanceof ServiceException && 
						ServiceExceptionType.IDENTIFICATION_COMPTE_UTILISATEUR_VEROUILLE.getLibelle().equals( ((ServiceException)object).getMessage()))
					return "/message/consultermail.jsf";
				return null;
			}
		});
		//onServiceNotificationEventEnabled = true;
	}
	
	@Override
	protected void onDefaultSubmitAction() throws Exception {
		CompteUtilisateur compteUtilisateur = null;
		compteUtilisateur = compteUtilisateurService.authentifier(credentials);
        SecurityUtils.getSubject().login(new UsernamePasswordToken(getDto().getUsername(), getDto().getPassword(), remember));
        userSessionManager.setCompteUtilisateur(compteUtilisateur);
        SavedRequest savedRequest = WebUtils.getAndClearSavedRequest(Faces.getRequest());
        Faces.redirect(savedRequest != null ? savedRequest.getRequestUrl() : navigationManager.url("espacePrivee",false).substring(1));
	}
	
	
	
	@Override
	public Credentials getDto() {
		return credentials;
	}
	
}
