package ci.gouv.budget.solde.sigdcp.controller.identification;

import java.io.IOException;
import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.Valid;

import lombok.Getter;

import org.omnifaces.util.Faces;

import ci.gouv.budget.solde.sigdcp.controller.ui.form.AbstractFormUIController;
import ci.gouv.budget.solde.sigdcp.model.identification.CompteUtilisateur;
import ci.gouv.budget.solde.sigdcp.model.identification.Credentials;
import ci.gouv.budget.solde.sigdcp.model.identification.Verrou;
import ci.gouv.budget.solde.sigdcp.service.ServiceException;
import ci.gouv.budget.solde.sigdcp.service.identification.CompteUtilisateurService;

@Named @ViewScoped
public class DeverouillerCompteUtilisateurController extends AbstractFormUIController<CompteUtilisateur> implements Serializable {
 
	private static final long serialVersionUID = 6591392098578555259L;
	
	/*
	 * Services
	 */
	@Inject private CompteUtilisateurService compteUtilisateurService;
	
	/*
	 * Dtos
	 */
	@Valid
	@Getter private Verrou verrou = new Verrou();
	
	@Getter private Credentials credentials = new Credentials();
	
	@Override
	protected void initialisation() {
		super.initialisation();
		try {
			compteUtilisateurService.deverouillable(verrou);
		} catch (ServiceException e) {
			messageManager.addError(e);
			try {
				Faces.redirect("message/badurl.jsf");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			return;
		}
		
		switch(verrou.getCause()){
		case ACCESS_MULTIPLE:
			title = "Déverouillage de votre compte utilisateur";
			defaultSubmitCommand.setNotificationMessageId("notification.compte.deverouille");
			break;
		case REINITIALISATION_PASSWORD:
			title = "Réinitialisation de votre mot de passe";
			defaultSubmitCommand.setNotificationMessageId("notification.compte.passwordreinit");
			break;
		}
		defaultSubmitCommand.setValue("bouton.valider");
		defaultSubmitCommand.setSuccessOutcome("login");
		
	}
	
	@Override
	protected void onDefaultSubmitAction() throws Exception {
		compteUtilisateurService.deverouiller(verrou,credentials);
	}
	
	
}
