package ci.gouv.budget.solde.sigdcp.service.calendrier;

import java.util.Collection;

import ci.gouv.budget.solde.sigdcp.model.calendrier.LettreAvance;
import ci.gouv.budget.solde.sigdcp.model.identification.Section;
import ci.gouv.budget.solde.sigdcp.service.AbstractService;

public interface LettreAvanceService extends AbstractService<LettreAvance, Long> {
	
	public void enregistrer(LettreAvance lettreAvance);

	
	Collection<LettreAvance> findBySection(Collection<Section> sections);
	 
}
