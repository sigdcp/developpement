package ci.gouv.budget.solde.sigdcp.service.identification;

import java.util.Collection;

import ci.gouv.budget.solde.sigdcp.model.identification.Inscription;
import ci.gouv.budget.solde.sigdcp.service.AbstractService;
import ci.gouv.budget.solde.sigdcp.service.ServiceException;

public interface InscriptionService extends AbstractService<Inscription,String> {
 
	/**
	 * Inscrit une inscription dans le système
	 * @param <T>
	 * @param inscription
	 * @throws ServiceException
	 */
	void inscrire(Inscription inscription) throws ServiceException ;
	
	/**
	 * Accepte une inscription afin de lui permettre d'acceder au système
	 * @param inscription
	 * @throws ServiceException
	 */
	void accepterInscription(Inscription inscription) throws ServiceException;
	
	void accepterInscription(Collection<Inscription> inscriptions) throws ServiceException;
	
	void rejeterInscription(Inscription inscription) throws ServiceException;
	
	void rejeterInscription(Collection<Inscription> inscriptions) throws ServiceException;
	
	/**
	 * Retourne la liste des inscriptions à valider
	 * @return
	 */
	//Collection<Inscription> getInscriptionsAValider();
	//a renommer
	Collection<Inscription> findInscriptionsAValiderByTypePersonneId(String typePersonneId);
	
	Collection<Inscription> findInscriptionsAValider();
	
}
