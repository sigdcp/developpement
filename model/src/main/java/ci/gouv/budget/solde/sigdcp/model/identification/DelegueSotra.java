/**
*    Syst�me Int�gr� de Gestion des D�penses Communes de Personnel - SIGDCP
*
*    Mod�le M�tier
*
**/


package ci.gouv.budget.solde.sigdcp.model.identification;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;
import ci.gouv.budget.solde.sigdcp.model.AbstractModel;

@Getter @Setter 
@Entity
public class DelegueSotra extends AbstractModel<Long>   implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id @GeneratedValue private Long id;
	
	@NotNull(message="L'agent est non valide")
	@ManyToOne private AgentEtat agent;
	
	@NotNull(message="L'interimaire est non valide")
	@ManyToOne private AgentEtat interimaire;
	
	@NotNull(message="La section gerée est non valide")
	@ManyToOne private Section sectionGeree;
	
	public DelegueSotra(AgentEtat agent, AgentEtat interimaire,Section sectionGeree) {
		super();
		this.agent = agent;
		this.interimaire = interimaire;
		this.sectionGeree = sectionGeree;
	}
	
	public DelegueSotra() {
		super();
	}
	
}