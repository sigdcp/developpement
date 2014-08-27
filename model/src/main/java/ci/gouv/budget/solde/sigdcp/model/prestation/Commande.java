/**
*    Syst�me Int�gr� de Gestion des D�penses Communes de Personnel - SIGDCP
*
*    Mod�le M�tier
*
**/


package ci.gouv.budget.solde.sigdcp.model.prestation;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Transient;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ci.gouv.budget.solde.sigdcp.model.dossier.PieceProduite;

/**
 * Bon de commande d'une prestation
 * @author Christian Yao Komenan
 *
 */
@Getter @Setter @Entity @NoArgsConstructor
public class Commande  extends PieceProduite  implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Transient private Prestataire prestataire;
	
	public Commande(String numero) {
		super();
		this.numero = numero;
	}
	
	
}