package ci.gouv.budget.solde.sigdcp.controller.demande;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import ci.gouv.budget.solde.sigdcp.model.identification.AgentEtat;
import ci.gouv.budget.solde.sigdcp.model.identification.Personne;

public class IdentitePersonneDTO implements Serializable {

	private static final long serialVersionUID = 1673292991759713069L;
	
	@Getter private Personne personne;
	
	@Getter @Setter
	private Boolean agentEtat=null,showQuestionAgentEtat=Boolean.TRUE,editable=Boolean.TRUE,readPieceIdentite=Boolean.FALSE,
			readSexe=Boolean.TRUE,readProfession=Boolean.TRUE,readNationalite=Boolean.TRUE,readContact=Boolean.TRUE,readDateNaissance=Boolean.TRUE;

	public IdentitePersonneDTO(Personne personne) {
		super();
		this.personne = personne;
	}

	public IdentitePersonneDTO(Personne personne,Boolean agentEtat,Boolean editable) {
		super();
		this.personne = personne;
		this.agentEtat = agentEtat;
		this.editable = editable;
	}

	public AgentEtat getAsAgentEtat(){
		return (AgentEtat) personne;
	}
	
}
