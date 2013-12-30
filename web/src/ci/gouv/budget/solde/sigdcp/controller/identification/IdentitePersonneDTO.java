package ci.gouv.budget.solde.sigdcp.controller.identification;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import ci.gouv.budget.solde.sigdcp.model.identification.InfosInscriptionPersonne;

public class IdentitePersonneDTO implements Serializable {

	private static final long serialVersionUID = 1673292991759713069L;
	
	@Getter
	private InfosInscriptionPersonne personne;
	
	@Getter @Setter
	private Boolean agentEtat;
	
	@Getter @Setter
	private String tp;

	public IdentitePersonneDTO(InfosInscriptionPersonne personne) {
		super();
		this.personne = personne;
	}

	public IdentitePersonneDTO(InfosInscriptionPersonne personne,Boolean agentEtat) {
		super();
		this.personne = personne;
		this.agentEtat = agentEtat;
		
		
	}

	
	
}
