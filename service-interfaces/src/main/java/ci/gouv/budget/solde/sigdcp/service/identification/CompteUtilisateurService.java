package ci.gouv.budget.solde.sigdcp.service.identification;

import ci.gouv.budget.solde.sigdcp.model.identification.CompteUtilisateur;
import ci.gouv.budget.solde.sigdcp.service.AbstractService;
import ci.gouv.budget.solde.sigdcp.service.ServiceException;

public interface CompteUtilisateurService extends AbstractService<CompteUtilisateur,String> {

	/**
	 * Active un compte utilisateur , en général lors de la première connexion
	 * @param username
	 * @param password
	 * @throws ServiceException
	 */
	void activer(String username,String password) throws ServiceException ;
	
	/**
	 * Authentifie un compte auprès du système
	 * @param username
	 * @param password
	 * @return
	 * @throws ServiceException
	 */
	CompteUtilisateur authentifier(String username,String password) throws ServiceException ;
	
	/**
	 * Deconnecte un compte du système
	 * @param compteUtilisateur
	 * @throws ServiceException
	 */
	void deconnecter(CompteUtilisateur compteUtilisateur) throws ServiceException ;
	
}
