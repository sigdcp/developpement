/**
*    Syst�me Int�gr� de Gestion des D�penses Communes de Personnel - SIGDCP
*
*    Mod�le M�tier
*
**/


package ci.gouv.budget.solde.sigdcp.model.identification;

import java.io.Serializable;

import javax.persistence.Entity;

import lombok.Getter;
import lombok.Setter;
import ci.gouv.budget.solde.sigdcp.model.DynamicEnumeration;

@Getter @Setter 
@Entity
public class Fonction  extends DynamicEnumeration  implements Serializable{

	private static final long serialVersionUID = 1L;
	
	//private Boolean horsGroupe = Boolean.FALSE;
	
	public Fonction() {}

	public Fonction(String code, String libelle/*,Boolean horsGroupe*/) {
		super(code, libelle);
		//this.horsGroupe = horsGroupe;
	}
	/*
	public Fonction(String code, String libelle) {
		this(code,libelle,false);
	}*/
	
}