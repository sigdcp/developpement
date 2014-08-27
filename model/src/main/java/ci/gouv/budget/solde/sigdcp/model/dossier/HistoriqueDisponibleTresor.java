/**
*    Syst�me Int�gr� de Gestion des D�penses Communes de Personnel - SIGDCP
*
*    Mod�le M�tier
*
**/


package ci.gouv.budget.solde.sigdcp.model.dossier;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import ci.gouv.budget.solde.sigdcp.model.AbstractModel;

@Getter @Setter 
/*@Entity*/ @EqualsAndHashCode(of={"id"},callSuper=false)
public class HistoriqueDisponibleTresor  extends AbstractModel<Long>  implements Serializable{

	private static final long serialVersionUID = 583414085631495905L;
	
	@Id @GeneratedValue
	private Long id;
	
	@ManyToOne
	@NotNull
	private CategorieDeplacement categorieDeplacement;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date date;
	
	private BigDecimal montant;
	
	public HistoriqueDisponibleTresor() {}

	
	
	
	
}