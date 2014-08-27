/**
*    Syst�me Int�gr� de Gestion des D�penses Communes de Personnel - SIGDCP
*
*    Mod�le M�tier
*
**/


package ci.gouv.budget.solde.sigdcp.model.dossier;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import ci.gouv.budget.solde.sigdcp.model.AbstractModel;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter 
@Entity
@Table(name="DOSSIERPJ") @Deprecated
public class DossierPieceJustificative  extends AbstractModel<Long>  implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id @GeneratedValue
	private Long id;
	
	@ManyToOne
	private Dossier dossier;
	
	@ManyToOne
	private PieceJustificative pieceJustificative;
	
	public DossierPieceJustificative() {}

	public DossierPieceJustificative(Dossier dossier,
			PieceJustificative pieceJustificative) {
		super();
		this.dossier = dossier;
		this.pieceJustificative = pieceJustificative;
	}

}