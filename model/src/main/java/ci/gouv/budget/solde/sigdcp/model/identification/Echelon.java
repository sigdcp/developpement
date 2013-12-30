/**
*    Syst�me Int�gr� de Gestion des D�penses Communes de Personnel - SIGDCP
*
*    Mod�le M�tier
*
**/


package ci.gouv.budget.solde.sigdcp.model.identification;

import java.io.Serializable;

import javax.persistence.Entity;

import lombok.Getter;
import lombok.Setter;
import ci.gouv.budget.solde.sigdcp.model.DynamicEnumeration;

@Getter @Setter 
@Entity
public class Echelon  extends DynamicEnumeration  implements Serializable{

	private static final long serialVersionUID = 1L;
	
	public Echelon() {}

	public Echelon(String code, String libelle) {
		super(code, libelle);
	}
	
	
}