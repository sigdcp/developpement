/**
*    Syst�me Int�gr� de Gestion des D�penses Communes de Personnel - SIGDCP
*
*    Mod�le M�tier
*
**/


package ci.gouv.budget.solde.sigdcp.model.identification;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter 
@Entity
public class DelegueSotra  extends AgentEtat   implements Serializable{

	private static final long serialVersionUID = 1L;

	@ManyToOne
	private Direction direction;
}