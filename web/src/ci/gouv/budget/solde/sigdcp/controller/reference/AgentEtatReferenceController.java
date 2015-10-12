package ci.gouv.budget.solde.sigdcp.controller.reference;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import ci.gouv.budget.solde.sigdcp.model.identification.AgentEtatReference;
import ci.gouv.budget.solde.sigdcp.service.identification.AgentEtatReferenceService;

@Named @ViewScoped
public class AgentEtatReferenceController extends AbstractEntiteReferenceController<AgentEtatReference, String> implements Serializable {

	private static final long serialVersionUID = -1061481987684469949L;
	
	@Inject AgentEtatReferenceService agentEtatReference;
	
	@Override
	protected String nomEntite() {
		return "Fonctionnaires et Agent de l'état";
	}

	@Override
	protected Class<AgentEtatReference> clazz() {
		return AgentEtatReference.class;
	}
	
	@Override
	protected List<AgentEtatReference> load() {
		return new ArrayList<>(genericService.findAllByClass(AgentEtatReference.class, "matricule"));
	}
	
	@Override
	public String identifiant(AgentEtatReference entity) {
		return entity.getMatricule();
	}
	

}
