package ci.gouv.budget.solde.sigdcp.model.identification;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ci.gouv.budget.solde.sigdcp.model.AbstractModel;

@Entity
@Getter @Setter @NoArgsConstructor
public class QuestionSecrete extends AbstractModel<Long> implements Serializable {

	private static final long serialVersionUID = -1426919647717880937L;
	
	@Id @GeneratedValue
	private Long id;
	
	private String libelle;

	public QuestionSecrete(String libelle) {
		super();
		this.libelle = libelle;
	}

	@Override
	public String toString() {
		return libelle;
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
		QuestionSecrete other = (QuestionSecrete) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
	
}
