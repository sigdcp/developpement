package ci.gouv.budget.solde.sigdcp.controller.identification;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import lombok.Getter;
import lombok.Setter;
import ci.gouv.budget.solde.sigdcp.controller.ui.AbstractEntityListUIController;
import ci.gouv.budget.solde.sigdcp.model.identification.CompteUtilisateur;
import ci.gouv.budget.solde.sigdcp.model.identification.Party;
import ci.gouv.budget.solde.sigdcp.model.identification.Role;
import ci.gouv.budget.solde.sigdcp.service.identification.CompteUtilisateurService;

public abstract class AbstractCompteUtilisateurListeController extends AbstractEntityListUIController<CompteUtilisateur> implements Serializable {

	private static final long serialVersionUID = 3488387909642345919L;

	@Inject private CompteUtilisateurService compteUtilisateurService;
	
	@Getter @Setter private Role role;
	
	@Override
	protected void initialisation() {
		super.initialisation();
		title="Gestion des profiles";
		listTitle="Liste des comptes utilisateurs";
		enableSearch();
		defaultSubmitCommand.setRendered(false);
		
	}
	
	@Override
	protected List<CompteUtilisateur> load() {
		role = role();
		if(role==null)
			return new ArrayList<>();
		return new ArrayList<>(compteUtilisateurService.findByTypeByRole(typeUtilisateur(), role));
	}
	
	protected abstract Class<? extends Party> typeUtilisateur();
	
	protected Role role(){
		return null;
	}
	
	@Override
	protected ProcessingType getProcessingType() {
		return ProcessingType.SINGLE;
	}
	
	@Override
	protected void hrefParameters(Map<String, Object> parameters,CompteUtilisateur compteUtilisateur) {
		super.hrefParameters(parameters, compteUtilisateur);
		parameters.put(webConstantResources.getRequestParamCompteUtilisateur(),compteUtilisateur.getId());
	}
	
	@Override
	public Boolean actionable(CompteUtilisateur compteUtilisateur) {
		return false;
	}
	
	@Override
	public String actionLabel(CompteUtilisateur compteUtilisateur) {
		return "Modifier";
	}
	
	@Override
	public String actionHref(CompteUtilisateur compteUtilisateur) {
		return _href(compteUtilisateur, webConstantResources.getRequestParamCrudUpdate());
	}
	
}
