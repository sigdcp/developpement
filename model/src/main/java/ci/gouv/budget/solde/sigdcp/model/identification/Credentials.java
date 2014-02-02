package ci.gouv.budget.solde.sigdcp.model.identification;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.Size;

import ci.gouv.budget.solde.sigdcp.model.utils.validation.groups.System;
import lombok.AllArgsConstructor;
import lombok.Data;

@Embeddable @Data @AllArgsConstructor
public class Credentials implements Serializable {

	private static final long serialVersionUID = -3099832512046879464L;
	
	@Column(unique=true)
	@Size(min=8,message="Le nom d'utilisateur doit avoir 8 caractères au minimum",groups=System.class)
	private String username;
	
	@Column(nullable=false)
	@Size(min=8,message="Le mot de passe doit avoir 8 caractères au minimum",groups=System.class)
	private String password;
	
	public Credentials() {}
	
}
