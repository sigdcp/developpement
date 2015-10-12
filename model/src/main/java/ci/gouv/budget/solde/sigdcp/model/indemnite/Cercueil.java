/**
*    Syst�me Int�gr� de Gestion des D�penses Communes de Personnel - SIGDCP
*
*    Mod�le M�tier
*
**/


package ci.gouv.budget.solde.sigdcp.model.indemnite;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;
import ci.gouv.budget.solde.sigdcp.model.DynamicEnumeration;

@Getter @Setter 
@Entity
public class Cercueil  extends DynamicEnumeration  implements Serializable{

	private static final long serialVersionUID = 1L;

	//@Column(precision=10,scale=2)
	//@NotNull
	private BigDecimal montant;
	
	public Cercueil() {
		super();
	}

	public Cercueil(String code, String libelle,BigDecimal montant) {
		super(code, libelle);
		this.montant=montant;
	}

	
}