/**
*    Syst�me Int�gr� de Gestion des D�penses Communes de Personnel - SIGDCP
*
*    Mod�le M�tier
*
**/


package ci.gouv.budget.solde.sigdcp.model.calendrier;

import java.io.Serializable;

import javax.persistence.Embeddable;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @Embeddable @EqualsAndHashCode
public class GroupeParticipantId implements Serializable{

	private static final long serialVersionUID = 1L;

	private Long missionId;
	private String groupeId;
}