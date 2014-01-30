package ci.gouv.budget.solde.sigdcp.dao.identification;

import ci.gouv.budget.solde.sigdcp.dao.DataAccessObject;
import ci.gouv.budget.solde.sigdcp.model.identification.CompteUtilisateur;

public interface CompteUtilisateurDao extends DataAccessObject<CompteUtilisateur,Long> {

	
	/**
	 * Retourne le compte utilisateur associé à ces paramètres de connexion
	 * @param username
	 * @param passworg
	 * @return
	 */
	CompteUtilisateur findByUsernameByPassword(String username,String password);
	
	CompteUtilisateur readByMatricule(String matricule);
	
}
