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
	
}
