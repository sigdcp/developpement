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

import lombok.Getter;
import lombok.Setter;

@Getter @Setter 
@Entity
public class BordereauTransmission  extends PieceProduite  implements Serializable{

	private static final long serialVersionUID = 1L;
	
	//@OneToMany
		//private LinkedList<PieceProduite> pieceProduites = new LinkedList<PieceProduite>();
	
	//private Integer montantPlafond;
	
	public BordereauTransmission() {
		super();
	}

	public BordereauTransmission(String numero, TypePieceProduite type, Date dateEtablissement/*,Integer montantPlafond*/) {
		super(numero, type,dateEtablissement);
		//this.montantPlafond=montantPlafond;
	}
	
	public BordereauTransmission(BordereauTransmission bordereauTransmission) {
		this(bordereauTransmission.numero,bordereauTransmission.getType(),bordereauTransmission.getDateEtablissement());
		setId(bordereauTransmission.getId());
	}
	
	
}