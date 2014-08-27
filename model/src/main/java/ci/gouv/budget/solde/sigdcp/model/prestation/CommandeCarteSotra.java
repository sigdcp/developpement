/**
*    Syst�me Int�gr� de Gestion des D�penses Communes de Personnel - SIGDCP
*
*    Mod�le M�tier
*
**/


package ci.gouv.budget.solde.sigdcp.model.prestation;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Getter;
import lombok.Setter;
import ci.gouv.budget.solde.sigdcp.model.sotra.AchatCarteSotra;

@Getter @Setter @Entity
//@Table(name="COMMANDECS")
public class CommandeCarteSotra  extends Commande  implements Serializable{

	private static final long serialVersionUID = 1L;

	@ManyToOne private AchatCarteSotra achat;

	@Temporal(TemporalType.DATE)
	private Date dateDistributionSotra;
	
	@Temporal(TemporalType.DATE)
	private Date dateDistributionDelegue;
	
	public CommandeCarteSotra() {}

	public CommandeCarteSotra(String numero,AchatCarteSotra achat) {
		super(numero);
		this.achat = achat;
	}

	public Long getAchatId(){
		return achat.getId();
	}
	
	
}