/**
*    Syst�me Int�gr� de Gestion des D�penses Communes de Personnel - SIGDCP
*
*    Mod�le M�tier
*
**/


package ci.gouv.budget.solde.sigdcp.model.dossier;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Getter;
import lombok.Setter;
import ci.gouv.budget.solde.sigdcp.model.AbstractModel;
import ci.gouv.budget.solde.sigdcp.model.identification.Personne;

@Getter @Setter 
@Entity
public class Operation  extends AbstractModel<Long>  implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id @GeneratedValue
	private Long id;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date date;
	
	@ManyToOne
	private NatureOperation nature;
	
	@ManyToOne
	private Personne creePar;
	
	public Operation() {}

	public Operation(Date date, NatureOperation nature,Personne creePar) {
		super();
		this.date = date;
		this.nature = nature;
		this.creePar = creePar;
	}
	
	
}