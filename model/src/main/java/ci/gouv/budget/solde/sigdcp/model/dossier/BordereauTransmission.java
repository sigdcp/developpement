/**
*    Syst�me Int�gr� de Gestion des D�penses Communes de Personnel - SIGDCP
*
*    Mod�le M�tier
*
**/


package ci.gouv.budget.solde.sigdcp.model.dossier;

import java.util.Date;
import java.util.LinkedList;

import ci.gouv.budget.solde.sigdcp.model.dossier.PieceProduite;
import ci.gouv.budget.solde.sigdcp.model.AbstractModel;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Getter @Setter 
@Entity
public class BordereauTransmission  extends AbstractModel<Long>  implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	private Long id;
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateEtablissement;
	@OneToMany
	private LinkedList<PieceProduite> pieceProduite = new LinkedList<PieceProduite>();
	@ManyToOne
	private TypeBordereauTransmission type;
}