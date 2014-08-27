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
//@Table(name="COMMANDETT")
public class CommandeTitreTransport  extends Commande  implements Serializable{

	private static final long serialVersionUID = 1L;

	@ManyToOne private PrestataireDemandeCotationMission prestataireDemandeCotationMission;
	
	private String observation;
	
	
	

	public CommandeTitreTransport(String numero, PrestataireDemandeCotationMission prestataireDemandeCotationMission) {
		super(numero);
		this.prestataireDemandeCotationMission = prestataireDemandeCotationMission;
	}
	
	public Long getCommandeId(){
		return prestataireDemandeCotationMission.getId().getDemandeCotationMissionId();
	}
	
}