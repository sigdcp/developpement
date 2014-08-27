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

import lombok.Getter;
import lombok.Setter;

@Getter @Setter 
@Entity 
public class FeuilleDeplacement  extends PieceProduite  implements Serializable{

	private static final long serialVersionUID = 1L;
	

	/**
	 * Le dossier concerné
	 */
	@ManyToOne
	protected Dossier dossier;
	
	
	
	public FeuilleDeplacement() {
		super();
	}

	public FeuilleDeplacement(Dossier dossier,String numero, TypePieceProduite type, Date dateEtablissement) {
		super(numero, type,dateEtablissement);
		this.dossier = dossier;
	}
	
	
	
}