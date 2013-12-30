/**
*    Système Intégré de Gestion des Dépenses Communes de Personnel - SIGDCP
*
*    Modèle Métier
*
**/


package ci.gouv.budget.solde.sigdcp.model.indemnite;

import ci.gouv.budget.solde.sigdcp.model.AbstractModel;
import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;

@Getter @Setter 
@Entity
public class Parametre  extends AbstractModel<Long>  implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	private Long id;
	private String tableSource;
	private String colonneSource;
	private String tableCible;
	private String colonneCible;
}