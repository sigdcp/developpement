package ci.gouv.budget.solde.sigdcp.model.stats;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;
import ci.gouv.budget.solde.sigdcp.model.dossier.NatureDeplacement;

@Getter @Setter
public class AbstractDeplacementStatistiques<DETAILS> implements Serializable {

	private static final long serialVersionUID = -250719730993390986L;

	// en entrees
	protected Date debut,fin;
	protected Collection<NatureDeplacement> natureDeplacements;
	
	// en sorties
	protected DeplacementStatistiquesResultats total = new DeplacementStatistiquesResultats();

	private Map<DETAILS,DeplacementStatistiquesResultats> resultatParNatureDeplacement = new HashMap<DETAILS, DeplacementStatistiquesResultats>();
	
}
