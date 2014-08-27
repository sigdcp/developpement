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
import javax.persistence.Transient;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter 
@Entity
//@Table(name="PIECEJ")
public class PieceJustificative  extends Document  implements Serializable{

	private static final long serialVersionUID = 1L;
		
	@ManyToOne
	private PieceJustificativeAFournir model;
	
	/**
	 * Le dossier concerné
	 */
	@ManyToOne
	protected Dossier dossier;
	
	/**
	 * Vrai si modifiable , Faux sinon , Null si depend du conteneur
	 */
	@Transient protected Boolean modifiable,editable;
	
	public PieceJustificative() {}

	public PieceJustificative(Dossier dossier,PieceJustificativeAFournir model) {
		super();
		this.dossier =dossier;
		this.model = model;
	}

	public PieceJustificative(Dossier dossier,String numero, PieceJustificativeAFournir model,Date dateEtablissement) {
		super(numero);
		this.model = model;
		this.dossier=dossier;
		this.dateEtablissement=dateEtablissement;
	}
	
	@Override
	public String toString() {
		return model.toString();
	}
	
}