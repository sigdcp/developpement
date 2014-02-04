package ci.gouv.budget.solde.sigdcp.controller.identification;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.Valid;

import lombok.Getter;
import ci.gouv.budget.solde.sigdcp.controller.ui.form.AbstractFormUIController;
import ci.gouv.budget.solde.sigdcp.model.identification.CompteUtilisateur;
import ci.gouv.budget.solde.sigdcp.model.identification.Credentials;
import ci.gouv.budget.solde.sigdcp.model.identification.Verrou;
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
		title = "Déverouillage de compte utilisateur";
		defaultSubmitCommand.setValue("bouton.valider");
	}
	
	@Override
	protected void onDefaultSubmitAction() throws Exception {
		compteUtilisateurService.deverouiller(verrou,credentials);
	}
	
	/*
	@Override
	protected InitWhen initWhen() {
		// TODO Auto-generated method stub
		return InitWhen.POST_CONSTRUCT;
	}*/
}
