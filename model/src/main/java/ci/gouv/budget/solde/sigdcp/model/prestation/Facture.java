/**
*    Syst�me Int�gr� de Gestion des D�penses Communes de Personnel - SIGDCP
*
*    Mod�le M�tier
*
**/


package ci.gouv.budget.solde.sigdcp.model.prestation;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ci.gouv.budget.solde.sigdcp.model.dossier.Document;
import ci.gouv.budget.solde.sigdcp.model.traitement.Traitable;
import ci.gouv.budget.solde.sigdcp.model.traitement.TraitementFacture;

@Getter @Setter 
@Entity @NoArgsConstructor
public class Facture  extends Document  implements Serializable{

	private static final long serialVersionUID = 1L;

	@OneToOne
	private Commande commande;
	
	@Column(precision=10,scale=2,name="FACT_MONTANT")
	private BigDecimal montant;
	
	@Embedded private Traitable<TraitementFacture> traitable = new Traitable<>();
	
	public Facture(Commande commande) {
		super();
		this.commande = commande;
	}
	
	public String getNumeroCommande(){
		return commande.getNumero();
	}
	
}