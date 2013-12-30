/**
*    Syst�me Int�gr� de Gestion des D�penses Communes de Personnel - SIGDCP
*
*    Mod�le M�tier
*
**/


package ci.gouv.budget.solde.sigdcp.model.geographie;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

import lombok.Getter;
import lombok.Setter;
import ci.gouv.budget.solde.sigdcp.model.AbstractModel;

@Getter @Setter 
@Entity 
public class DistanceEntreLocalite extends AbstractModel<DistanceEntreLocaliteId>   implements Serializable{

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private DistanceEntreLocaliteId id;
	private Float distanceKm;

}