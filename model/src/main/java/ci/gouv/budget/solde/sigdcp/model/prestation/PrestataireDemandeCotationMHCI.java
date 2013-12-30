/**
*    Syst�me Int�gr� de Gestion des D�penses Communes de Personnel - SIGDCP
*
*    Mod�le M�tier
*
**/


package ci.gouv.budget.solde.sigdcp.model.prestation;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

import lombok.Getter;
import lombok.Setter;
import ci.gouv.budget.solde.sigdcp.model.AbstractModel;

@Getter @Setter 
@Entity
public class PrestataireDemandeCotationMHCI extends AbstractModel<PrestataireDemandeCotationMHCIId> implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	private PrestataireDemandeCotationMHCIId id;
}