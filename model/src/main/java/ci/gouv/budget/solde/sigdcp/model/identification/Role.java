package ci.gouv.budget.solde.sigdcp.model.identification;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter @AllArgsConstructor
public enum Role {

	SUPERUSER("superuser"),
	ADMINISTRATEUR("agentetat"),
	AGENT_ETAT("agentetat"),
	GESTIONNAIRE_CARTE_SOTRA("gestionnairecartesotra"),
	LIQUIDATEUR("liquidateur"),
	RESPONSABLE("responsable"),
	DIRECTEUR("directeur"),
	PAYEUR("payeur")
	
	;
	
	private String code;
}