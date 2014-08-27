package ci.gouv.budget.solde.sigdcp.service.prestation;

import java.util.Collection;

import ci.gouv.budget.solde.sigdcp.model.prestation.Prestataire;
import ci.gouv.budget.solde.sigdcp.model.stats.DeplacementStatistiques;

public interface PrestataireService extends AbstractPrestataireService<Prestataire> {

	Collection<Prestataire> findByType(String typePrestataire);

	DeplacementStatistiques<Prestataire> findConsommationBilletAvionStatistique(Collection<Prestataire> prestataires); 
	
}
