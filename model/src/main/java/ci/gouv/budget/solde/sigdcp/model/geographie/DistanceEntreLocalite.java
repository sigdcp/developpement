/**
*    Syst�me Int�gr� de Gestion des D�penses Communes de Personnel - SIGDCP
*
*    Mod�le M�tier
*
**/


package ci.gouv.budget.solde.sigdcp.model.geographie;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;
import ci.gouv.budget.solde.sigdcp.model.AbstractModel;

@Getter @Setter 
@Entity 
@Table(name="DISTANCE")
public class DistanceEntreLocalite extends AbstractModel<DistanceEntreLocaliteId>   implements Serializable{

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private DistanceEntreLocaliteId id;
	
	@NotNull @Column(nullable=false,precision=10,scale=2)
	private BigDecimal distanceKm;
	
	@Transient private Localite localite1,localite2;
	
	public DistanceEntreLocalite() {}
	
	public DistanceEntreLocalite(Localite ville1,Localite ville2,String distanceKm) {
		id = new DistanceEntreLocaliteId(ville1.getCode(), ville2.getCode());
		this.distanceKm = new BigDecimal(distanceKm);
	}

}