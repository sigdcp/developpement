/**
*    Système Intégré de Gestion des Dépenses Communes de Personnel - SIGDCP
*
*    Modèle Métier
*
**/


package ci.gouv.budget.solde.sigdcp.model.prestation;

import ci.gouv.budget.solde.sigdcp.model.AbstractModel;
import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;

@Getter @Setter 
@Entity
public class Prestation  extends AbstractModel<String>  implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	private String code;
	private String libelle;
}