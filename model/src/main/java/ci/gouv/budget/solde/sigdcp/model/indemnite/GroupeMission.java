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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.Getter;
import lombok.Setter;
import ci.gouv.budget.solde.sigdcp.model.identification.Fonction;

@Getter @Setter 
@Entity
public class GroupeMission  extends Groupe  implements Serializable{

	private static final long serialVersionUID = 1L;

	@OneToMany(fetch=FetchType.EAGER)
	//@JoinTable(name="GROUPEFONCTION")
	@JoinColumn(name="GRPECODE")
	private Collection<Fonction> fonctions = new HashSet<>();
	
	@ManyToOne
	private TypeClasseVoyage classeVoyage;
	
	public GroupeMission() {
		super();
	}

	public GroupeMission(String code, String libelle) {
		super(code, libelle);
	}
	
	

}