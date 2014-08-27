package ci.gouv.budget.solde.sigdcp.model.identification;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import ci.gouv.budget.solde.sigdcp.model.utils.validation.groups.Client;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Embeddable @Getter @Setter @AllArgsConstructor @EqualsAndHashCode(of={"code","jeton"})
public class Verrou implements Serializable {

	private static final long serialVersionUID = -4449969354459932246L;

	public enum Cause{ACCESS_MULTIPLE,REINITIALISATION_PASSWORD,DESACTIVATION_COMPTE}
	
	@Column(unique=true)
	private String code;
	
	@Enumerated(EnumType.STRING)
	private Cause cause;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateVerouillage;
	
	/**
	 * Code devant etre saisi par l'utilisateur lors du deverouillage du compte
	 */
	@NotNull(groups=Client.class)
	private String jeton;
	
	public Verrou() {}
	
	
	
}
