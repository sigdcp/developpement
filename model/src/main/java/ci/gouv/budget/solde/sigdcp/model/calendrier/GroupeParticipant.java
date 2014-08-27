/**
*    Syst�me Int�gr� de Gestion des D�penses Communes de Personnel - SIGDCP
*
*    Mod�le M�tier
*
**/


package ci.gouv.budget.solde.sigdcp.model.calendrier;

import java.io.Serializable;

import javax.persistence.EmbeddedId;

import lombok.Getter;
import lombok.Setter;
import ci.gouv.budget.solde.sigdcp.model.AbstractModel;

@Getter @Setter 
//@Entity 
public class GroupeParticipant extends AbstractModel<GroupeParticipantId>   implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	private GroupeParticipantId id;
	private Integer nombre;
	
}