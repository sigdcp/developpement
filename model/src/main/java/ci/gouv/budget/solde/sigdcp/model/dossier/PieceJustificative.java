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
public class PieceJustificative  extends Document  implements Serializable{

	private static final long serialVersionUID = 1L;
		
	@ManyToOne
	private PieceJustificativeAFournir model;
	
	@ManyToOne
	private Dossier dossier;
	
	public PieceJustificative() {}

	public PieceJustificative(PieceJustificativeAFournir model) {
		super();
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