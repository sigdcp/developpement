/**
*    Syst�me Int�gr� de Gestion des D�penses Communes de Personnel - SIGDCP
*
*    Mod�le M�tier
*
**/


package ci.gouv.budget.solde.sigdcp.model.indemnite;

import ci.gouv.budget.solde.sigdcp.model.AbstractModel;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Getter @Setter 
@Entity
public class IndemniteTranche  extends AbstractModel<Long>  implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	private Long id;
	
	private String libelle;
	
	private Float montant;
	
	private Float intervalleMin;
	
	private Float intervalleMax;
	
	@ManyToOne
	private TypeIndemniteTranche type;
}