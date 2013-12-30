/**
*    Syst�me Int�gr� de Gestion des D�penses Communes de Personnel - SIGDCP
*
*    Mod�le M�tier
*
**/


package ci.gouv.budget.solde.sigdcp.model.dossier;

import java.io.Serializable;

import javax.persistence.Entity;

import lombok.Getter;
import lombok.Setter;
import ci.gouv.budget.solde.sigdcp.model.DynamicEnumeration;

@Getter @Setter 
@Entity
public class Statut  extends DynamicEnumeration  implements Serializable{

	private static final long serialVersionUID = 1L;

	private Integer rang;
	
	public Statut() {}

	public Statut(String code, String libelle, Integer rang) {
		super(code, libelle);
		this.rang = rang;
	}
	
	
}