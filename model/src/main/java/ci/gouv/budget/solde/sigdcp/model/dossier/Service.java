/**
*    Syst�me Int�gr� de Gestion des D�penses Communes de Personnel - SIGDCP
*
*    Mod�le M�tier
*
**/


package ci.gouv.budget.solde.sigdcp.model.dossier;

import java.io.Serializable;

import javax.persistence.Entity;

import lombok.Getter;
import lombok.Setter;
import ci.gouv.budget.solde.sigdcp.model.DynamicEnumeration;

@Getter @Setter 
@Entity
@Deprecated
/**
 * To be replaced by <code>Section</code>
 * @author christian
 *
 */
public class Service  extends DynamicEnumeration  implements Serializable{

	private static final long serialVersionUID = 1L;

	public Service() {}

	public Service(String code, String libelle) {
		super(code, libelle);
	}

	
}