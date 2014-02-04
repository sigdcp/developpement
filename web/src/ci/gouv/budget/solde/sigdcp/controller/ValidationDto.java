package ci.gouv.budget.solde.sigdcp.controller;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import ci.gouv.budget.solde.sigdcp.model.utils.validation.groups.Client;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ValidationDto implements Serializable {

	private static final long serialVersionUID = -1339594859941400459L;

	
	private String questionLibelle;
	@NotNull(groups=Client.class)
	private Boolean accepter;
	
	public ValidationDto(String questionLibelle) {
		super();
		this.questionLibelle = questionLibelle;
	}
	
	
}
