package ci.gouv.budget.solde.sigdcp.service.identification;

import java.util.Collection;

import ci.gouv.budget.solde.sigdcp.model.identification.Section;
import ci.gouv.budget.solde.sigdcp.model.stats.DeplacementStatistiques;
import ci.gouv.budget.solde.sigdcp.service.AbstractService;


public interface SectionService extends AbstractService<Section,String> {

	DeplacementStatistiques<Section> findStatistique(Collection<Section> sections);

	DeplacementStatistiques<Section> findLettreAvanceStatistique(Collection<Section> sections);
	
}
