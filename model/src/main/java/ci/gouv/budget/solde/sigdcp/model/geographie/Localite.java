/**
*    Syst�me Int�gr� de Gestion des D�penses Communes de Personnel - SIGDCP
*
*    Mod�le M�tier
*
**/


package ci.gouv.budget.solde.sigdcp.model.geographie;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ci.gouv.budget.solde.sigdcp.model.DynamicEnumeration;

@Getter @Setter 
@Entity @AllArgsConstructor
public class Localite  extends DynamicEnumeration  implements Serializable{

	private static final long serialVersionUID = 1L;

	@ManyToOne
	private Localite parent;
	
	@ManyToOne
	private TypeLocalite type;
	
	public Localite() {}

	public Localite(String code, String libelle, Localite parent,
			TypeLocalite type) {
		super(code, libelle);
		this.parent = parent;
		this.type = type;
	}
	
	
}