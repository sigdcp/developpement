package ci.gouv.budget.solde.sigdcp.model.dossier;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor @Getter
public enum ValidationType {
	
	ACCEPTER("Accepter"),
	DIFFERER("Differer"),
	REJETER("Rejeter"),
	
	;
	
	private String libelle;

}
