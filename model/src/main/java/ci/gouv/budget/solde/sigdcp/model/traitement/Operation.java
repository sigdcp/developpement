/**
*    Syst�me Int�gr� de Gestion des D�penses Communes de Personnel - SIGDCP
*
*    Mod�le M�tier
*
**/


package ci.gouv.budget.solde.sigdcp.model.traitement;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
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
	@Column(name="DAT")
	private Date date;
	
	@ManyToOne
	private NatureOperation nature;
	
	@ManyToOne
	private Personne effectuePar;
	
	public Operation() {}

	public Operation(Date date, NatureOperation nature,Personne effectuePar) {
		super();
		this.date = date;
		this.nature = nature;
		this.effectuePar = effectuePar;
	}
	
	
}