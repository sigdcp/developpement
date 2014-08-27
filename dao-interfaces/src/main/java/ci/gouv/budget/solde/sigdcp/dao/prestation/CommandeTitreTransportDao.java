package ci.gouv.budget.solde.sigdcp.dao.prestation;

import java.util.Collection;

import ci.gouv.budget.solde.sigdcp.model.calendrier.MissionExecutee;
import ci.gouv.budget.solde.sigdcp.model.prestation.CommandeTitreTransport;

public interface CommandeTitreTransportDao extends AbstractCommandeDao<CommandeTitreTransport> {

	CommandeTitreTransport readByMission(MissionExecutee mission);
	
	/**
	 * Liste des commandes ayant le statut visées d'un prestataire
	 * @param prestataire
	 * @return
	 */
	Collection<CommandeTitreTransport> readByPrestataire(Long prestataire);
	
}
 