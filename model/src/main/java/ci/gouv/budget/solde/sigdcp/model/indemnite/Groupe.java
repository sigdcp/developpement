/**
*    Syst�me Int�gr� de Gestion des D�penses Communes de Personnel - SIGDCP
*
*    Mod�le M�tier
*
**/


package ci.gouv.budget.solde.sigdcp.model.indemnite;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import lombok.Getter;
import lombok.Setter;
import ci.gouv.budget.solde.sigdcp.model.DynamicEnumeration;
import ci.gouv.budget.solde.sigdcp.model.identification.Grade;

@Getter @Setter 
@Entity @Inheritance(strategy=InheritanceType.SINGLE_TABLE)
public class Groupe  extends DynamicEnumeration  implements Serializable{

	private static final long serialVersionUID = 1L;

	@ManyToMany(fetch=FetchType.EAGER)
	//@JoinTable(name="GROUPEGRADE")
	 @JoinTable(name="GROUPEGRADE",joinColumns = { @JoinColumn(name = "GRPECODE") } ,inverseJoinColumns={ @JoinColumn(name = "GRADECODE") })
	private Collection<Grade> grades = new HashSet<>();
	
	public Groupe() {}

	public Groupe(String code, String libelle) {
		super(code, libelle);
	}
	
	
}