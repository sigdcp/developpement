/**
*    Syst�me Int�gr� de Gestion des D�penses Communes de Personnel - SIGDCP
*
*    Mod�le M�tier
*
**/


package ci.gouv.budget.solde.sigdcp.model.indemnite;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ci.gouv.budget.solde.sigdcp.model.AbstractModel;

@Getter @Setter 
@Entity @AllArgsConstructor @NoArgsConstructor
@Table(name="LOCGPEM")
public class LocaliteGroupeMission extends AbstractModel<LocaliteGroupeMissionId>   implements Serializable{

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private LocaliteGroupeMissionId id;
	
	@Column(precision=10,scale=2)
	private BigDecimal indemnite;
	
}