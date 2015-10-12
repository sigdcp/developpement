package ci.gouv.budget.solde.sigdcp.service.traitement;

import java.util.Collection;

import ci.gouv.budget.solde.sigdcp.model.AbstractModel;
import ci.gouv.budget.solde.sigdcp.service.AbstractService;
import ci.gouv.budget.solde.sigdcp.service.ServiceException;

public interface TraitableService<TRAITABLE extends AbstractModel<TID>,TID> extends AbstractService<TRAITABLE,TID> {

	/**
	 * Une nouvelle objet,un objet specific ou l'objet courant courant (Basé sur les regles de gestions)
	 * @param id
	 * @param natureOperationCode
	 * @return
	 * @throws ServiceException
	 */
	TRAITABLE findDemande(Long id,String natureOperationCode) throws ServiceException;
	
	TRAITABLE find(TID id,String natureOperationCode) throws ServiceException;
	
	/**
	 * Valide l'opération sollicitée
	 * @param traitables
	 * @throws ServiceException
	 */
	void valider(Collection<TRAITABLE> traitables) throws ServiceException;
	
	/**
	 * La liste des objets a traiter pour cette opération
	 * @param natureOperationCode
	 * @return
	 */
	Collection<TRAITABLE> findATraiter(String natureOperationCode);
	
	/**
	 * Initialise un objet avec diverses informations - Concept de vue
	 * @param traitable
	 * @param natureOperationCode
	 */
	void init(TRAITABLE traitable,String natureOperationCode);
	
	/**
	 * La liste des objets de l'utilisateur
	 * @return
	 */
	Collection<TRAITABLE> findDemandes();
	Collection<TRAITABLE> findDemandesSolde();

}
