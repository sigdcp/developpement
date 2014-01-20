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
public class BulletinLiquidation  extends PieceProduite  implements Serializable{

	private static final long serialVersionUID = 1L;
	
	//@OneToMany
		//private LinkedList<PieceProduite> pieceProduites = new LinkedList<PieceProduite>();
	
	@ManyToOne
	private BordereauTransmission bordereauTransmission;
	
	private Integer montant;
	
	public BulletinLiquidation() {
		super();
	}

	public BulletinLiquidation(String numero, TypePieceProduite type, Date dateEtablissement,BordereauTransmission bordereauTransmission,Integer montant) {
		super(numero, type,dateEtablissement);
		this.bordereauTransmission = bordereauTransmission;
		this.montant=montant;
	}
	
	
	
	
}