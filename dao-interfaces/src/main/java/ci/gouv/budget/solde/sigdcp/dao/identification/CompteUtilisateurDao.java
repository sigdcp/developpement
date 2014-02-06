package ci.gouv.budget.solde.sigdcp.dao.identification;

import ci.gouv.budget.solde.sigdcp.dao.DataAccessObject;
import ci.gouv.budget.solde.sigdcp.model.identification.CompteUtilisateur;
import ci.gouv.budget.solde.sigdcp.model.identification.Credentials;
import ci.gouv.budget.solde.sigdcp.model.identification.Verrou.Cause;

public interface CompteUtilisateurDao extends DataAccessObject<CompteUtilisateur,Long> {

	CompteUtilisateur readByUsername(String username);
	
	CompteUtilisateur readByCredentials(Credentials credentials);
	
	CompteUtilisateur readByMatricule(String matricule);
	
	CompteUtilisateur readByEMail(String email);
	
	CompteUtilisateur readByCodeVerrouByCauseVerrou(String code,Cause cause);
	
}
