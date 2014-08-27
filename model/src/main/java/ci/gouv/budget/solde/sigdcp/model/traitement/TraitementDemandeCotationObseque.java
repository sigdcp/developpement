/**
*    Syst�me Int�gr� de Gestion des D�penses Communes de Personnel - SIGDCP
*
*    Mod�le M�tier
*
**/


package ci.gouv.budget.solde.sigdcp.model.traitement;

import java.io.Serializable;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;
import ci.gouv.budget.solde.sigdcp.model.prestation.DemandeCotationObseque;

@Getter @Setter 
@Entity
@DiscriminatorValue("TDCO")
//@Table(name="TRAITDEMCOTOBS")
public class TraitementDemandeCotationObseque  extends Traitement  implements Serializable{

	private static final long serialVersionUID = 583414085631495905L;
	
	@ManyToOne
	/*@JoinColumn(nullable=false)
	@NotNull*/
	private DemandeCotationObseque demandeCotationObseque;
	
	public TraitementDemandeCotationObseque() {}

	public TraitementDemandeCotationObseque(Operation operation, Statut statut, DemandeCotationObseque demandeCotationObseque) {
		super(operation, statut);
		this.demandeCotationObseque = demandeCotationObseque;
	}

	
	
}