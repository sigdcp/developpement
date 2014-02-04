package ci.gouv.budget.solde.sigdcp.model.identification;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ci.gouv.budget.solde.sigdcp.model.AbstractModel;
import ci.gouv.budget.solde.sigdcp.model.utils.validation.groups.Client;

@Entity
@Getter @Setter @NoArgsConstructor
public class ReponseSecrete extends AbstractModel<Long> implements Serializable {

	private static final long serialVersionUID = -1426919647717880937L;
	
	@Id @GeneratedValue
	private Long id;
	
	@ManyToOne 
	@NotNull(groups=Client.class)
	private QuestionSecrete question;
	
	@NotNull(groups=Client.class)
	private String libelle;

	public ReponseSecrete(QuestionSecrete question,String libelle) {
		super();
		this.question = question;
		this.libelle = libelle;
	}
	
	public ReponseSecrete(QuestionSecrete question) {
		this(question,null);
	}

	@Override
	public String toString() {
		return libelle;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((question == null) ? 0 : question.hashCode());
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
		ReponseSecrete other = (ReponseSecrete) obj;
		if (question == null) {
			if (other.question != null)
				return false;
		} else if (!question.equals(other.question))
			return false;
		return true;
	}
	
	
	
}
