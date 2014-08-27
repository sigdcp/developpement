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

import javax.persistence.ManyToMany;

import lombok.Getter;
import lombok.Setter;
import ci.gouv.budget.solde.sigdcp.model.DynamicEnumeration;

@Getter @Setter 
//@Entity
public class GroupeTypePie  extends DynamicEnumeration  implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@ManyToMany
	private Collection<TypePiece> typePieces = new LinkedList<>();
	
	public GroupeTypePie() {}

	public GroupeTypePie(String code, String libelle) {
		super(code, libelle);
	}
	
	
}