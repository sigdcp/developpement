package ci.gouv.budget.solde.sigdcp.controller.identification;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import lombok.Getter;
import lombok.Setter;
import ci.gouv.budget.solde.sigdcp.controller.ui.form.AbstractFormUIController;
import ci.gouv.budget.solde.sigdcp.model.identification.CompteUtilisateur;
import ci.gouv.budget.solde.sigdcp.model.identification.Personne;
import ci.gouv.budget.solde.sigdcp.service.identification.CompteUtilisateurService;
import ci.gouv.budget.solde.sigdcp.service.resources.CRUDType;

@Named @RequestScoped
public class RecupererCompteController extends AbstractFormUIController<CompteUtilisateur> implements Serializable {
 
	private static final long serialVersionUID = 6591392098578555259L;
	
	/*
	 * Services
	 */
	@Inject private CompteUtilisateurService compteUtilisateurService;
	

	/*
	 * Dtos
	 */
	
	@Getter private CompteUtilisateur compteUtilisateur = new CompteUtilisateur();
	@Getter @Setter private Boolean remember = Boolean.FALSE;
	@Getter @Setter private String matricule;
	
	@Override
	protected InitWhen initWhen() {
		return InitWhen.POST_CONSTRUCT;
	}
	
	@Override
	protected void initialisation() {
		crudType = CRUDType.CREATE;
		super.initialisation();
		title = "Récupérer mot de passe oublié";
		defaultSubmitCommand.setValue(text("bouton.valider"));
		//compteUtilisateur.setPersonne(new Personne());
		
	}
	
	@Override
	protected void onDefaultSubmitAction() {
		
	}
	
	@Override
	public CompteUtilisateur getDto() {
		return compteUtilisateur;
	}
	
	
	
}
