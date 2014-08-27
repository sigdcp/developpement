/**
*    Syst�me Int�gr� de Gestion des D�penses Communes de Personnel - SIGDCP
*
*    Mod�le M�tier
*
**/


package ci.gouv.budget.solde.sigdcp.model.indemnite;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import ci.gouv.budget.solde.sigdcp.model.DynamicEnumeration;

@Getter @Setter 
@Entity
@Table(name="TYPECLV")
public class TypeClasseVoyage  extends DynamicEnumeration  implements Serializable{

	private static final long serialVersionUID = 1L;

	public TypeClasseVoyage() {
		super();
	}

	public TypeClasseVoyage(String code, String libelle) {
		super(code, libelle);
	}

	
}