/**
*    Syst�me Int�gr� de Gestion des D�penses Communes de Personnel - SIGDCP
*
*    Mod�le M�tier
*
**/


package ci.gouv.budget.solde.sigdcp.model.dossier;

import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedList;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;

import lombok.Getter;
import lombok.Setter;
import ci.gouv.budget.solde.sigdcp.model.DynamicEnumeration;

@Getter @Setter 
@Entity
public class GroupeTypePiece  extends DynamicEnumeration  implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@ManyToMany
	private Collection<TypePiece> typePieces = new LinkedList<>();
	
	public GroupeTypePiece() {}

	public GroupeTypePiece(String code, String libelle) {
		super(code, libelle);
	}
	
	
}