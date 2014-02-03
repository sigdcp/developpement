package ci.gouv.budget.solde.sigdcp.model.identification;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ci.gouv.budget.solde.sigdcp.model.AbstractModel;

@Entity
@Getter @Setter @NoArgsConstructor
public class ReponseSecrete extends AbstractModel<Long> implements Serializable {

	private static final long serialVersionUID = -1426919647717880937L;
	
	@Id @GeneratedValue
	private Long id;
	
	@ManyToOne
	private QuestionSecrete question;
	
	private String libelle;

	public ReponseSecrete(QuestionSecrete question,String libelle) {
		super();
		this.question = question;
		this.libelle = libelle;
	}

	@Override
	public String toString() {
		return libelle;
	}
	
}
