/**
*    Syst�me Int�gr� de Gestion des D�penses Communes de Personnel - SIGDCP
*
*    Mod�le M�tier
*
**/


package ci.gouv.budget.solde.sigdcp.model.indemnite;

import java.io.Serializable;

import javax.persistence.Entity;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter 
@Entity
public class GroupeMission  extends Groupe  implements Serializable{

	private static final long serialVersionUID = 1L;

	public GroupeMission() {
		super();
	}

	public GroupeMission(String code, String libelle) {
		super(code, libelle, null, null, null);
	}
	
	

}