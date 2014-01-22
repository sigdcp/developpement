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
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter 
@Entity @AllArgsConstructor
public class PieceProduite  extends Document  implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@ManyToOne
	private TypePieceProduite type;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateEtablissement;
	
	public PieceProduite() {}

	public PieceProduite(String numero, TypePieceProduite type, Date dateEtablissement) {
		super(numero);
		this.type = type;
		this.dateEtablissement=dateEtablissement;
	}
	
}