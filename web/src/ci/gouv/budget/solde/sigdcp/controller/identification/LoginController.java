package ci.gouv.budget.solde.sigdcp.controller.identification;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import lombok.Getter;
import lombok.Setter;
import ci.gouv.budget.solde.sigdcp.controller.AbstractFormUIController;
import ci.gouv.budget.solde.sigdcp.controller.application.UserSessionManager;
import ci.gouv.budget.solde.sigdcp.service.identification.CompteUtilisateurService;

@Named @RequestScoped
public class LoginController extends AbstractFormUIController implements Serializable {
 
	private static final long serialVersionUID = 6591392098578555259L;
	
	@Inject private CompteUtilisateurService compteUtilisateurService;
	@Inject UserSessionManager userSessionManager;
	
	@Getter @Setter private String nomUtilisateur;
	@Getter @Setter private String motDePasse;
		
	@Override
	protected void action() {
		userSessionManager.setCompte(compteUtilisateurService.authentifier(nomUtilisateur, motDePasse));
	}
		
	@Override
	protected String succes() {
		return "index";
	}
	

}
