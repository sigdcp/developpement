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
import ci.gouv.budget.solde.sigdcp.model.prestation.DemandeCotationMission;

@Getter @Setter 
@Entity
@DiscriminatorValue("TDCM")
//@Table(name="TRAITDEMCOTM")
public class TraitementDemandeCotationMission  extends Traitement  implements Serializable{

	private static final long serialVersionUID = 583414085631495905L;
	
	@ManyToOne
	/*@JoinColumn(nullable=false)
	@NotNull*/
	private DemandeCotationMission demandeCotationMission;
	
	public TraitementDemandeCotationMission() {}

	public TraitementDemandeCotationMission(Operation operation, Statut statut, DemandeCotationMission demandeCotationMission) {
		super(operation, statut);
		this.demandeCotationMission = demandeCotationMission;
	}

	
	
}