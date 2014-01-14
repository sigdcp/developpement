/**
*    Syst�me Int�gr� de Gestion des D�penses Communes de Personnel - SIGDCP
*
*    Mod�le M�tier
*
**/


package ci.gouv.budget.solde.sigdcp.model.dossier;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter 
@Entity @AllArgsConstructor
public class PieceProduite  extends Document  implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@ManyToOne
	private TypePieceProduite type;
	
	public PieceProduite() {}

	public PieceProduite(String numero, TypePieceProduite type) {
		super(numero);
		this.type = type;
	}
	
	
}