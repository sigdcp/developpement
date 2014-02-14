package ci.gouv.budget.solde.sigdcp.model.dossier;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable @Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Courrier implements Serializable {

	private static final long serialVersionUID = -3015414113393307367L;

	@Column(name="numerocourrier",unique=true)
	private String numero;
	
	@Column(name="datedepot")
	@Temporal(TemporalType.TIMESTAMP)
	private Date date;
	
}
