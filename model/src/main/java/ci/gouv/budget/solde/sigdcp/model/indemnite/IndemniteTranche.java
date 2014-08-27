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
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;
import ci.gouv.budget.solde.sigdcp.model.AbstractModel;

@Getter @Setter @Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@Entity
@Table(name="INDEMTRAN")
public class IndemniteTranche  extends AbstractModel<Long>  implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id @GeneratedValue
	private Long id;

	@Column(precision=10,scale=2)
	@NotNull
	protected BigDecimal intervalleMin;
	
	@Column(precision=10,scale=2)
	@NotNull
	protected BigDecimal intervalleMax;
	
	public IndemniteTranche() {}
	
	public IndemniteTranche(BigDecimal intervalleMin, BigDecimal intervalleMax) {
		super();
		this.intervalleMin = intervalleMin;
		this.intervalleMax = intervalleMax;
	}
	
	
	
}