package ci.gouv.budget.solde.sigdcp.service.indemnite;

import java.util.Collection;

import ci.gouv.budget.solde.sigdcp.model.indemnite.GroupeMission;
import ci.gouv.budget.solde.sigdcp.model.indemnite.TypeClasseVoyage;
import ci.gouv.budget.solde.sigdcp.model.stats.DeplacementStatistiques;
import ci.gouv.budget.solde.sigdcp.service.AbstractService;

public interface TypeClasseVoyageService extends AbstractService<TypeClasseVoyage,String> {

	DeplacementStatistiques<TypeClasseVoyage> findStatistiqueByTypeClasseVoyage(Collection<TypeClasseVoyage> typeClasseVoyages);
	DeplacementStatistiques<GroupeMission> findStatistiqueByGroupe(Collection<GroupeMission> groupes);
	
}
