package ci.gouv.budget.solde.sigdcp.service.identification;

import java.util.Collection;

import ci.gouv.budget.solde.sigdcp.model.identification.Souscription;
import ci.gouv.budget.solde.sigdcp.service.AbstractService;
import ci.gouv.budget.solde.sigdcp.service.ServiceException;

public interface AbstractSouscriptionService<SOUSCRIPTION extends Souscription> extends AbstractService<SOUSCRIPTION,String> {
 
	/**
	 * Inscrit une inscription dans le système
	 * @param <T>
	 * @param inscription
	 * @throws ServiceException
	 */
	void souscrire(SOUSCRIPTION souscription) throws ServiceException ;
	
	/**
	 * Accepte une inscription afin de lui permettre d'acceder au système
	 * @param inscription
	 * @throws ServiceException
	 */
	void accepter(SOUSCRIPTION souscription) throws ServiceException;
	
	void accepter(Collection<SOUSCRIPTION> souscriptions) throws ServiceException;
	
	void rejeter(SOUSCRIPTION souscription) throws ServiceException;
	
	void rejeter(Collection<SOUSCRIPTION> souscriptions) throws ServiceException;
	
	/**
	 * Retourne la liste des inscriptions à valider
	 * @return
	 */
	//Collection<Souscription> getSouscriptionsAValider();
	//a renommer
	Collection<SOUSCRIPTION> findSouscriptionsAValiderByTypePersonneId(String typePersonneId);
	
	Collection<SOUSCRIPTION> findSouscriptionsAValider();
	
}
