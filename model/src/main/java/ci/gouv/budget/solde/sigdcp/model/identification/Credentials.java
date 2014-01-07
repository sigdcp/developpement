package ci.gouv.budget.solde.sigdcp.model.identification;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Data;

@Embeddable @Data @AllArgsConstructor
public class Credentials implements Serializable {

	private static final long serialVersionUID = -3099832512046879464L;
	
	@Column(unique=true)
	private String username;
	
	@Column(nullable=false)
	private String password;
	
	public Credentials() {}
	
}
