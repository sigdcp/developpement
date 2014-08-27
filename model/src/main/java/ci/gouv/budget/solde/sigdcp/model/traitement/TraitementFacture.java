/**
*    Syst�me Int�gr� de Gestion des D�penses Communes de Personnel - SIGDCP
*
*    Mod�le M�tier
*
**/


package ci.gouv.budget.solde.sigdcp.model.traitement;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;
import ci.gouv.budget.solde.sigdcp.model.prestation.Facture;

@Getter @Setter 
@Entity
//@Table(name="TRAITFACTURE")
public class TraitementFacture  extends Traitement  implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@ManyToOne
	@JoinColumn
	@NotNull
	private Facture facture;
	
	public TraitementFacture() {}

	public TraitementFacture(Operation operation, Statut statut, Facture facture) {
		super(operation, statut);
		this.facture = facture;
	}

	

//	
}