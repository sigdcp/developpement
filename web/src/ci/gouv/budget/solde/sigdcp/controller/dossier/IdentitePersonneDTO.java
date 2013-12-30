package ci.gouv.budget.solde.sigdcp.controller.dossier;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import ci.gouv.budget.solde.sigdcp.model.identification.AgentEtat;

public class IdentitePersonneDTO implements Serializable {

	private static final long serialVersionUID = 1673292991759713069L;
	
	@Getter private AgentEtat personne;
	
	@Getter @Setter
	private Boolean agentEtat,showQuestionAgentEtat;

	public IdentitePersonneDTO(AgentEtat personne) {
		super();
		this.personne = personne;
	}

	public IdentitePersonneDTO(AgentEtat personne,Boolean agentEtat) {
		super();
		this.personne = personne;
		this.agentEtat = agentEtat;
	}

	
	
}
