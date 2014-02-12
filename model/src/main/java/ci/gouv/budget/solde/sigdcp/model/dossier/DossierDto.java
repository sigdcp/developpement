package ci.gouv.budget.solde.sigdcp.model.dossier;

import java.io.Serializable;

import ci.gouv.budget.solde.sigdcp.model.AbstractModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @AllArgsConstructor
public class DossierDto extends AbstractModel<String> implements Serializable {

	private static final long serialVersionUID = -8969802819862478365L;

	private Dossier dossier;
	private Statut statut;
	
	public String getNumero(){
		return dossier.getNumero();
	}
	
}
