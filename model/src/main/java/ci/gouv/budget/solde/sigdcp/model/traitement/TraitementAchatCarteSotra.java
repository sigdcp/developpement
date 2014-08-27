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
import ci.gouv.budget.solde.sigdcp.model.sotra.AchatCarteSotra;

@Getter @Setter 
@Entity
//@Table(name="TRAITACS")
public class TraitementAchatCarteSotra  extends Traitement  implements Serializable{

	private static final long serialVersionUID = 583414085631495905L;
	
	@ManyToOne
	@JoinColumn
	@NotNull
	private AchatCarteSotra achat;
	
	public TraitementAchatCarteSotra() {}

	public TraitementAchatCarteSotra(Operation operation, Statut statut, AchatCarteSotra achat) {
		super(operation, statut);
		this.achat = achat;
	}

	
	
}