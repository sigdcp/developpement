package ci.gouv.budget.solde.sigdcp.model.dossier;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable @Getter @Setter @NoArgsConstructor
public class Courrier implements Serializable {

	private static final long serialVersionUID = -3015414113393307367L;

	@Column(name="NUMEROCOURRIER")
	private String numero;
	
	@Column(name="DATEDEPOT")
	@Temporal(TemporalType.TIMESTAMP)
	private Date date;

	/**/
	
	@Transient private String uiLibelle;
	
	/**/
	
	public Courrier(String numero, Date date) {
		super();
		this.numero = numero;
		this.date = date;
	}
	
	
	
	
	
}
