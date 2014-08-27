/**
*    Syst�me Int�gr� de Gestion des D�penses Communes de Personnel - SIGDCP
*
*    Mod�le M�tier
*
**/


package ci.gouv.budget.solde.sigdcp.model.indemnite;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter 
@Entity
public class GroupeDD  extends Groupe   implements Serializable{

	private static final long serialVersionUID = 1L;

	/* Agent */
	
	@Column(precision=10,scale=2) @NotNull
	private BigDecimal montantAgent;
	
	@Column(precision=10,scale=2) @NotNull
	private BigDecimal poidsAutoriseAgent;
	
	/* Epouse */
	
	@Column(precision=10,scale=2) @NotNull
	private BigDecimal montantEpouse;
	
	@Column(precision=10,scale=2) @NotNull
	private BigDecimal poidsAutoriseEpouse;
	
	/* Enfant */
	
	@Column(precision=10,scale=2) @NotNull
	private BigDecimal montantEnfant;
	
	@Column(precision=10,scale=2) @NotNull
	private BigDecimal poidsAutoriseEnfant;

	public GroupeDD() {}
	
	public GroupeDD(String code, String libelle, BigDecimal montantAgent, BigDecimal poidsAutoriseAgent, BigDecimal montantEpouse, BigDecimal poidsAutoriseEpouse, BigDecimal montantEnfant,
			BigDecimal poidsAutoriseEnfant) {
		super(code, libelle);
		this.montantAgent = montantAgent;
		this.poidsAutoriseAgent = poidsAutoriseAgent;
		this.montantEpouse = montantEpouse;
		this.poidsAutoriseEpouse = poidsAutoriseEpouse;
		this.montantEnfant = montantEnfant;
		this.poidsAutoriseEnfant = poidsAutoriseEnfant;
	}
	
	
	
}