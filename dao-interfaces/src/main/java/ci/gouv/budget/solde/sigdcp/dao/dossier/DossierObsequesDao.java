package ci.gouv.budget.solde.sigdcp.dao.dossier;

import java.util.Collection;

import ci.gouv.budget.solde.sigdcp.model.dossier.DossierObseques;

public interface DossierObsequesDao extends AbstractDossierDao<DossierObseques> {

	Collection<DossierObseques> readACoter();

}
  