package ci.gouv.budget.solde.sigdcp.dao.prestation;

import java.util.Collection;

import ci.gouv.budget.solde.sigdcp.model.prestation.Prestataire;

public interface PrestataireDao extends AbstractPrestataireDao<Prestataire> {

	Collection<Prestataire> readByType(String typePrestataire);

	
}
 