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

import ci.gouv.budget.solde.sigdcp.controller.AbstractUIFormController;
import ci.gouv.budget.solde.sigdcp.service.identification.CompteUtilisateurService;

@Named @RequestScoped
public class ConnexionFormController extends AbstractUIFormController implements Serializable {
 
	private static final long serialVersionUID = 6591392098578555259L;
	
	public static final String HOME_URL = "private/espacePrive.jsf";
	
	@Inject private CompteUtilisateurService compteUtilisateurService;
	
	@Getter @Setter private String username;
	@Getter @Setter private String password;
	@Getter @Setter private Boolean remember = Boolean.FALSE;
	@Getter @Setter private String matricule;
	@Getter @Setter private String email;
	
	 
	
	@Override
	protected void action() {
		//compteUtilisateurService.authentifier(username, password);
		try {
            SecurityUtils.getSubject().login(new UsernamePasswordToken(username, password, remember));
            SavedRequest savedRequest = WebUtils.getAndClearSavedRequest(Faces.getRequest());
            Faces.redirect(savedRequest != null ? savedRequest.getRequestUrl() : HOME_URL);
        }
        catch (Exception e) {
        	throw new RuntimeException("Utilisateur inconnu");
        }
	}
	/*
	@Override
	protected String succes() {
		return "/private/espacePrive.jsf?faces-redirect=true";
	}
	*/

}
