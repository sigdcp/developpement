/**
*    Syst�me Int�gr� de Gestion des D�penses Communes de Personnel - SIGDCP
*
*    Mod�le M�tier
*
**/


package ci.gouv.budget.solde.sigdcp.model.indemnite;

import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;
import javax.persistence.Embeddable;

@Getter @Setter 
@Embeddable
public class MontantIndemniteMission    implements Serializable{

	private static final long serialVersionUID = 1L;

	private Float fraisMission;
	private Float transport;
	private Float divers;
}