/**
*    Syst�me Int�gr� de Gestion des D�penses Communes de Personnel - SIGDCP
*
*    Mod�le M�tier
*
**/


package ci.gouv.budget.solde.sigdcp.model.traitement;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import ci.gouv.budget.solde.sigdcp.model.DynamicEnumeration;

@Getter @Setter 
@Entity 
@Table(name="NATUREOP")
public class NatureOperation  extends DynamicEnumeration  implements Serializable{

	private static final long serialVersionUID = 1L;

	@ManyToOne
	private NatureOperation precedent;
	
	@ManyToOne
	private NatureOperation suivant;
	
	/**
	 * Message par defaut lorsque on nq succès
	 */
	private String message;
	
	public NatureOperation() {}

	public NatureOperation(String code, String libelle,String message) {
		super(code, libelle);
		this.message = message;
	}
	
}