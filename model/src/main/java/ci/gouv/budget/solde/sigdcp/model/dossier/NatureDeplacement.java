/**
*    Syst�me Int�gr� de Gestion des D�penses Communes de Personnel - SIGDCP
*
*    Mod�le M�tier
*
**/


package ci.gouv.budget.solde.sigdcp.model.dossier;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;
import ci.gouv.budget.solde.sigdcp.model.DynamicEnumeration;

@Getter @Setter 
@Entity 
public class NatureDeplacement  extends DynamicEnumeration  implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@ManyToOne
	private CategorieDeplacement categorie;
	
	private Integer nombreJourIndemniteJournaliere;
	
	public NatureDeplacement() {}

	public NatureDeplacement(CategorieDeplacement categorie,String code, String libelle,
			Integer nombreJourIndemniteJournaliere) {
		super(code, libelle);
		this.categorie = categorie;
		this.nombreJourIndemniteJournaliere = nombreJourIndemniteJournaliere;
	}

	@Override
	public String toString() {
		return categorie+"/"+libelle;
	}
	
	
}