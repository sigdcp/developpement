/**
*    Syst�me Int�gr� de Gestion des D�penses Communes de Personnel - SIGDCP
*
*    Mod�le M�tier
*
**/


package ci.gouv.budget.solde.sigdcp.model.indemnite;

import java.util.LinkedList;

import ci.gouv.budget.solde.sigdcp.model.identification.Categorie;
import ci.gouv.budget.solde.sigdcp.model.AbstractModel;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Getter @Setter 
@Entity
public class Groupe  extends AbstractModel<String>  implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	private String code;
	
	private String libelle;
	
	@OneToMany
	private LinkedList<Categorie> categories = new LinkedList<Categorie>();
	
	@ManyToOne
	private TypeGroupe type;
}