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

import lombok.Getter;
import lombok.Setter;

@Getter @Setter 
@Entity
public class PieceJustificative  extends Document  implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateEtablissement;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateEffetDecision;
		
	@ManyToOne
	private PieceJustificativeAFournir model;
	
	@ManyToOne
	private Dossier dossier;
	
	public PieceJustificative() {}

	public PieceJustificative(PieceJustificativeAFournir model,Dossier dossier) {
		super();
		this.model = model;
		this.dossier = dossier;
	}

	public PieceJustificative(String numero, PieceJustificativeAFournir model) {
		super(numero);
		this.model = model;
	}
	
	
	
}