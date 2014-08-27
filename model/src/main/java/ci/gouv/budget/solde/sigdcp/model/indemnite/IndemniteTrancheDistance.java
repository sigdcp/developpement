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
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;
import ci.gouv.budget.solde.sigdcp.model.dossier.CategorieDeplacement;

@Getter @Setter
@Entity
//@Table(name="INDEMTRDIST")
public class IndemniteTrancheDistance  extends IndemniteTranche  implements Serializable{

	private static final long serialVersionUID = 1L;

	@ManyToOne
	protected CategorieDeplacement categorieDeplacement;
	
	@Column(precision=10,scale=2)
	@NotNull
	private BigDecimal montant;
	
	public IndemniteTrancheDistance() {}
	
	public IndemniteTrancheDistance(CategorieDeplacement categorieDeplacement, BigDecimal intervalleMin, BigDecimal intervalleMax,BigDecimal montant) {
		super(intervalleMin,intervalleMax);
		this.categorieDeplacement = categorieDeplacement;
		this.montant = montant;
	}
	
	
	
}