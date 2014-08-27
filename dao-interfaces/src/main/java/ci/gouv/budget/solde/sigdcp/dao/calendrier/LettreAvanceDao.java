package ci.gouv.budget.solde.sigdcp.dao.calendrier;

import java.util.Collection;

import ci.gouv.budget.solde.sigdcp.dao.DataAccessObject;
import ci.gouv.budget.solde.sigdcp.model.calendrier.LettreAvance;
import ci.gouv.budget.solde.sigdcp.model.identification.Section;

public interface LettreAvanceDao extends DataAccessObject<LettreAvance,Long> {
	
	Collection<LettreAvance> readbySection(Collection<Section> sections);
	
}
