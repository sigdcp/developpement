/**
*    Syst�me Int�gr� de Gestion des D�penses Communes de Personnel - SIGDCP
*
*    Mod�le M�tier
*
**/


package ci.gouv.budget.solde.sigdcp.model.indemnite;

import java.io.Serializable;
import java.util.LinkedList;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.Getter;
import lombok.Setter;
import ci.gouv.budget.solde.sigdcp.model.DynamicEnumeration;
import ci.gouv.budget.solde.sigdcp.model.identification.Categorie;

@Getter @Setter 
@Entity @Inheritance(strategy=InheritanceType.JOINED)
public class Groupe  extends DynamicEnumeration  implements Serializable{

	private static final long serialVersionUID = 1L;

	//@OneToMany
	//private LinkedList<Categorie> categories = new LinkedList<Categorie>();
	
	@ManyToOne
	private TypeGroupe type;
	
	public Groupe() {}

	public Groupe(String code, String libelle, String description,LinkedList<Categorie> categories, TypeGroupe type) {
		super(code, libelle, description);
		//this.categories = categories;
		this.type = type;
	}
	
	
}