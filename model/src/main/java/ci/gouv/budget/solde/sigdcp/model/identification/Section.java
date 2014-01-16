/**
*    Syst�me Int�gr� de Gestion des D�penses Communes de Personnel - SIGDCP
*
*    Mod�le M�tier
*
**/


package ci.gouv.budget.solde.sigdcp.model.identification;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;
import ci.gouv.budget.solde.sigdcp.model.DynamicEnumeration;

@Getter @Setter 
@Entity 
public class Section  extends DynamicEnumeration  implements Serializable{

	private static final long serialVersionUID = 1L;

	@ManyToOne
	private TypeSection type;
	
	@ManyToOne
	private Section parent;
	
	public Section() {}

	public Section(Section parent,String code, String libelle,TypeSection type) {
		super(code, libelle);
		this.parent=parent;
		this.type = type;
	}
	
	
}