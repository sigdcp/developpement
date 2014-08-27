/**
*    Syst�me Int�gr� de Gestion des D�penses Communes de Personnel - SIGDCP
*
*    Mod�le M�tier
*
**/


package ci.gouv.budget.solde.sigdcp.model.indemnite;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Entity
//@Table(name="INDEMTRCER")
public class IndemniteTrancheCercueil  extends IndemniteTranche  implements Serializable{

	private static final long serialVersionUID = 1L;

	@ManyToOne
	private Cercueil cercueil;
	
	public IndemniteTrancheCercueil() {}
	
	public IndemniteTrancheCercueil(BigDecimal intervalleMin, BigDecimal intervalleMax,Cercueil cercueil) {
		super(intervalleMin,intervalleMax);
		this.cercueil = cercueil;
	}
	
	
	
}