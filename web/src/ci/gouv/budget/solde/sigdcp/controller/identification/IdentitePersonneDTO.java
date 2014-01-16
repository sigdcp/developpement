package ci.gouv.budget.solde.sigdcp.controller.identification;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import ci.gouv.budget.solde.sigdcp.model.identification.Personne;
import ci.gouv.budget.solde.sigdcp.model.identification.souscription.InfosSouscriptionComptePersonne;

public class IdentitePersonneDTO implements Serializable {

	private static final long serialVersionUID = 1673292991759713069L;
	
	@Getter
	private InfosSouscriptionComptePersonne infosSouscriptionComptePersonne;
	
	@Getter @Setter
	private Boolean agentEtat,editable;
	
	@Getter @Setter
	private String tp;

	public IdentitePersonneDTO(Boolean editable,InfosSouscriptionComptePersonne infosSouscriptionComptePersonne) {
		super();
		this.editable=editable;
		this.infosSouscriptionComptePersonne = infosSouscriptionComptePersonne;
	}

	public IdentitePersonneDTO(Boolean editable,InfosSouscriptionComptePersonne infosSouscriptionComptePersonne,Boolean agentEtat) {
		super();
		this.editable=editable;
		this.infosSouscriptionComptePersonne = infosSouscriptionComptePersonne;
		this.agentEtat = agentEtat;
		
		
	}
	
	public Personne getPersonne(){
		return infosSouscriptionComptePersonne.getPersonne();
	}
	
	
}
