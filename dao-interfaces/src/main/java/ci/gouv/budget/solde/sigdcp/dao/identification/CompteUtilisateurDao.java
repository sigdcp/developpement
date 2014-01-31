package ci.gouv.budget.solde.sigdcp.dao.identification;

import ci.gouv.budget.solde.sigdcp.dao.DataAccessObject;
import ci.gouv.budget.solde.sigdcp.model.identification.CompteUtilisateur;

public interface CompteUtilisateurDao extends DataAccessObject<CompteUtilisateur,Long> {

	CompteUtilisateur readByMatricule(String matricule);
	
	CompteUtilisateur readByEMail(String email);
	
}
