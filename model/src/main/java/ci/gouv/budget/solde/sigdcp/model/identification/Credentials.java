package ci.gouv.budget.solde.sigdcp.model.identification;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import ci.gouv.budget.solde.sigdcp.model.utils.validation.groups.Client;
import ci.gouv.budget.solde.sigdcp.model.utils.validation.groups.System;

@Embeddable @Getter @Setter @AllArgsConstructor @EqualsAndHashCode(of={"username","password"})
public class Credentials implements Serializable {

	private static final long serialVersionUID = -3099832512046879464L;
	
	@Column(unique=true)
	@NotNull(groups={Client.class,System.class})
	@Size(min=6,message="Le nom d'utilisateur doit avoir 8 caractères au minimum",groups=System.class)
	private String username;
	
	@Column(nullable=false)
	@NotNull(groups={Client.class,System.class})
	@Size(min=6,message="Le mot de passe doit avoir 8 caractères au minimum",groups=System.class)
	private String password;
	
	public Credentials() {}
	
	@Override
	public String toString() {
		return username+"/"+password;
	}
	
}
