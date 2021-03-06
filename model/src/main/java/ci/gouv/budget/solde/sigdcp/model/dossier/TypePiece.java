/**
*    Syst�me Int�gr� de Gestion des D�penses Communes de Personnel - SIGDCP
*
*    Mod�le M�tier
*
**/


package ci.gouv.budget.solde.sigdcp.model.dossier;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import lombok.Getter;
import lombok.Setter;
import ci.gouv.budget.solde.sigdcp.model.DynamicEnumeration;

@Getter @Setter 
@Entity @Inheritance(strategy=InheritanceType.SINGLE_TABLE)
public class TypePiece  extends DynamicEnumeration  implements Serializable{

	private static final long serialVersionUID = 1L;
	
	public TypePiece() {}

	public TypePiece(String code, String libelle) {
		super(code, libelle);
	}
	
	
}