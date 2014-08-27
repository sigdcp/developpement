package ci.gouv.budget.solde.sigdcp.service.geographie;

import java.util.Collection;

import ci.gouv.budget.solde.sigdcp.model.geographie.Localite;
import ci.gouv.budget.solde.sigdcp.model.stats.DeplacementStatistiques;
import ci.gouv.budget.solde.sigdcp.service.AbstractService;

public interface LocaliteService extends AbstractService<Localite,String> {

	Collection<Localite> findByTypeId(String typeLocalite);

	Collection<Localite> findByTypeIdByParent(String typeLocalite, Collection<Localite> parents);

	DeplacementStatistiques<Localite> findStatistiqueByTypeId(Collection<Localite> localites,String typeLocalite);

	DeplacementStatistiques<Localite> findConsommationBilletAvionStatistique(Collection<Localite> localites);
	
}
