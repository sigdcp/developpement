package ci.gouv.budget.solde.sigdcp.model.stats;

import java.io.Serializable;

import ci.gouv.budget.solde.sigdcp.model.traitement.Statut;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @AllArgsConstructor
public class TableauBordStatutDetails implements Serializable {

	private static final long serialVersionUID = -3770824542688984714L;

	private Statut statut;
	private Long count;
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((statut == null) ? 0 : statut.hashCode());
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
		TableauBordStatutDetails other = (TableauBordStatutDetails) obj;
		if (statut == null) {
			if (other.statut != null)
				return false;
		} else if (!statut.equals(other.statut))
			return false;
		return true;
	}
	
	
	
}
