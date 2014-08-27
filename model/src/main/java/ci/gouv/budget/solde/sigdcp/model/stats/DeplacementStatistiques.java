package ci.gouv.budget.solde.sigdcp.model.stats;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class DeplacementStatistiques<GROUPE> implements Serializable {

	private static final long serialVersionUID = -250719730993390986L;

	// en entrees
	protected Date debut,fin;
	protected Collection<GROUPE> groupes=new ArrayList<>();
	
	// en sorties
	protected DeplacementStatistiquesResultats total = new DeplacementStatistiquesResultats();

	private Map<GROUPE,DeplacementStatistiquesResultats> resultatParGroupe = new HashMap<GROUPE, DeplacementStatistiquesResultats>();
	
}
