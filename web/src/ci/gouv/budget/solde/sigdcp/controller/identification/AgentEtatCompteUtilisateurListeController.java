package ci.gouv.budget.solde.sigdcp.controller.identification;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import ci.gouv.budget.solde.sigdcp.model.Code;
import ci.gouv.budget.solde.sigdcp.model.identification.AgentEtat;
import ci.gouv.budget.solde.sigdcp.model.identification.Party;
import ci.gouv.budget.solde.sigdcp.model.identification.Role;

@Named @ViewScoped
public class AgentEtatCompteUtilisateurListeController extends AbstractCompteUtilisateurListeController implements Serializable {

	private static final long serialVersionUID = 3488387909642345919L;

	@Override
	protected Class<? extends Party> typeUtilisateur() {
		return AgentEtat.class;
	}
	
	@Override
	protected Role role() {
		return genericService.findByClass(Role.class, Code.ROLE_AGENT_ETAT);
	}

}
