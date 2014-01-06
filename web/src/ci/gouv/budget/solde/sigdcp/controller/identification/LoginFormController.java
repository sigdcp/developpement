package ci.gouv.budget.solde.sigdcp.controller.identification;

import java.io.Serializable;

import javax.annotation.PostConstruct;
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

import ci.gouv.budget.solde.sigdcp.controller.AbstractFormSubmitAction;
import ci.gouv.budget.solde.sigdcp.controller.AbstractFormUIController;
import ci.gouv.budget.solde.sigdcp.controller.NavigationManager;
import ci.gouv.budget.solde.sigdcp.model.identification.CompteUtilisateur;
import ci.gouv.budget.solde.sigdcp.service.identification.CompteUtilisateurService;

@Named @RequestScoped
public class LoginFormController extends AbstractFormUIController<CompteUtilisateur> implements Serializable {
 
	private static final long serialVersionUID = 6591392098578555259L;
	
	public static final String HOME_URL = "private/espacePrive.jsf";
	
	/*
	 * Services
	 */
	@Inject private CompteUtilisateurService compteUtilisateurService;
	

	/*
	 * Dtos
	 */
	@Getter CompteUtilisateur compte;
	
	/*
	 * CREATE Credentials Object {username,password}
	 */
	@Getter @Setter private String username;
	@Getter @Setter private String password;
	
	@Getter @Setter private Boolean remember = Boolean.FALSE;
	@Getter @Setter private String matricule;
	@Getter @Setter private String email;
	
	@PostConstruct
	private void postConstructConnexionFormController() {
		defaultSubmitAction = new AbstractFormSubmitAction<CompteUtilisateur>(compte,messageManager,"boutton.seconnecter","ui-icon-check",null,
				Boolean.TRUE,Boolean.TRUE,NavigationManager.OUTCOME_CURRENT_VIEW) {
			
			private static final long serialVersionUID = 2226036622897242355L;

			@Override
			protected void action() {
				try {
					compteUtilisateurService.authentifier(username, password);
		            SecurityUtils.getSubject().login(new UsernamePasswordToken(username, password, remember));
		            SavedRequest savedRequest = WebUtils.getAndClearSavedRequest(Faces.getRequest());
		            Faces.redirect(savedRequest != null ? savedRequest.getRequestUrl() : HOME_URL);
		        }
		        catch (Exception e) {
		        	throw new RuntimeException("Utilisateur inconnu");
		        }
			}
		};
	} 

}
