/**
*    Syst�me Int�gr� de Gestion des D�penses Communes de Personnel - SIGDCP
*
*    Mod�le M�tier
*
**/


package ci.gouv.budget.solde.sigdcp.model.prestation;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @Entity @NoArgsConstructor
//@Table(name="COMMANDEOBS")
public class CommandeObseque  extends Commande  implements Serializable{

	private static final long serialVersionUID = 1L;

	@ManyToOne private PrestataireDemandeCotationObseque prestataireDemandeCotationObseque;
	
	private String observation;
	
	
	

	public CommandeObseque(String numero, PrestataireDemandeCotationObseque prestataireDemandeCotationObseque) {
		super(numero);
		this.prestataireDemandeCotationObseque = prestataireDemandeCotationObseque;
	}
	
	public Long getCommandeId(){
		return prestataireDemandeCotationObseque.getId().getDemandeCotationObsequeId();
	}
	
}