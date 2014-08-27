package ci.gouv.budget.solde.sigdcp.dao.prestation;

import java.util.Collection;

import ci.gouv.budget.solde.sigdcp.model.prestation.CommandeObseque;

public interface CommandeObsequeDao extends AbstractCommandeDao<CommandeObseque> {

	Collection<CommandeObseque> readByPrestataire(Long prestataire);
	
}
 