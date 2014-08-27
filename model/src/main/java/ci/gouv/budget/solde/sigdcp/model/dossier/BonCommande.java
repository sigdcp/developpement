/**
*    Syst�me Int�gr� de Gestion des D�penses Communes de Personnel - SIGDCP
*
*    Mod�le M�tier
*
**/


package ci.gouv.budget.solde.sigdcp.model.dossier;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter 
@Entity
public class BonCommande  extends PieceProduite  implements Serializable{

	private static final long serialVersionUID = 1L;
		
	@Column(precision=15,scale=2,name="BC_MONTANT")
	private BigDecimal montant;
	
	public BonCommande() {
		super();
	}

	public BonCommande(String numero, TypePieceProduite type, Date dateEtablissement) {
		super(numero, type,dateEtablissement);
	}
	
	public BonCommande(BonCommande bonCommande) {
		this(bonCommande.numero,bonCommande.getType(),bonCommande.getDateEtablissement());
		setId(bonCommande.getId());
	}
	
	
}