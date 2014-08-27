/**
*    Syst�me Int�gr� de Gestion des D�penses Communes de Personnel - SIGDCP
*
*    Mod�le M�tier
*
**/


package ci.gouv.budget.solde.sigdcp.model.indemnite;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Getter;
import lombok.Setter;
import ci.gouv.budget.solde.sigdcp.model.AbstractModel;

@Getter @Setter 
@Entity
@Table(name="INDEMCAL")
public class IndemniteCalculee extends AbstractModel<IndemniteCalculeeId> implements Serializable{

	private static final long serialVersionUID = 1L;

	@EmbeddedId private IndemniteCalculeeId id;
	
	private BigDecimal montant;
	
	@Transient private RegleCalcul indemnite;
	
	public IndemniteCalculee() {}
	
	public IndemniteCalculee(IndemniteCalculeeId id, BigDecimal montant,RegleCalcul  regleCalcul) {
		super();
		this.id = id;
		this.montant = montant;
		this.indemnite = regleCalcul;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		IndemniteCalculee other = (IndemniteCalculee) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "IndemniteCalculee [id=" + id.getIndeminiteId() + ", montant=" + montant+" - "+indemnite
				+ "]";
	}
	
	
	
}