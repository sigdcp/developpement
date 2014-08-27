package ci.gouv.budget.solde.sigdcp.model.stats;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class DeplacementStatistiquesResultats implements Serializable {

	private static final long serialVersionUID = -5085950552240973702L;
	
	private Long nombreDossier=0l;
	private Long nombreVoyageurs=0l;
	private Long depense=0l,fraisTransport=0l;
	
}
