package ci.gouv.budget.solde.sigdcp.service.identification;

import ci.gouv.budget.solde.sigdcp.model.identification.Personne;
import ci.gouv.budget.solde.sigdcp.service.AbstractService;

/**
 * Definit l'ensembles des services d'identification de personnes
 * @author christian
 *
 * @param <PERSONNE>
 */
public interface AbstractPersonneService<PERSONNE extends Personne> extends AbstractService<PERSONNE,Long> {

	/**
	 * Inscrit une personne dans le système
	 * @param personne
	 * @throws ServiceException
	 */
	//void inscrire(PERSONNE personne) throws ServiceException ;
	
	/**
	 * Valide l'inscription d'une personne afin de lui permettre d'acceder au système
	 * @param personne
	 * @throws ServiceException
	 */
	//void validerInscription(PERSONNE personne) throws ServiceException;
	
	//void validerInscription(Collection<PERSONNE> personnes) throws ServiceException;
	
	/**
	 * Retourne la liste des inscriptions à valider
	 * @return
	 */
	//Collection<PERSONNE> getInscriptions();
}
