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

import ci.gouv.budget.solde.sigdcp.controller.AbstractFormUIController;
import ci.gouv.budget.solde.sigdcp.model.identification.Credentials;
import ci.gouv.budget.solde.sigdcp.service.identification.CompteUtilisateurService;

@Named @RequestScoped
public class LoginController extends AbstractFormUIController<Credentials> implements Serializable {
 
	private static final long serialVersionUID = 6591392098578555259L;
	
	public static final String HOME_URL = "private/espacePrive.jsf";
	
	/*
	 * Services
	 */
	@Inject private CompteUtilisateurService compteUtilisateurService;
	

	/*
	 * Dtos
	 */
	
	
	@Getter @Setter private Boolean remember = Boolean.FALSE;
	@Getter @Setter private String matricule;
	@Getter @Setter private String email;
	
	@PostConstruct
	private void postConstructConnexionFormController() {
		__firstPreRenderView__();
		defaultSubmitAction.setValue("bouton.seconnecter");
	}
	
	@Override
	protected void onDefaultSubmitAction() {
		try {
			compteUtilisateurService.authentifier(getDto().getUsername(), getDto().getPassword());
            SecurityUtils.getSubject().login(new UsernamePasswordToken(getDto().getUsername(), getDto().getPassword(), remember));
            SavedRequest savedRequest = WebUtils.getAndClearSavedRequest(Faces.getRequest());
            Faces.redirect(savedRequest != null ? savedRequest.getRequestUrl() : HOME_URL);
        }
        catch (Exception e) {
        	throw new RuntimeException("Utilisateur inconnu");
        }
	}
	
	@Override
	public boolean isCreate() {
		return Boolean.TRUE;
	}

}