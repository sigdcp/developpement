package ci.gouv.budget.solde.sigdcp.dao.identification;

import java.util.Collection;

import ci.gouv.budget.solde.sigdcp.dao.DataAccessObject;
import ci.gouv.budget.solde.sigdcp.model.identification.CompteUtilisateur;
import ci.gouv.budget.solde.sigdcp.model.identification.Credentials;
import ci.gouv.budget.solde.sigdcp.model.identification.Party;
import ci.gouv.budget.solde.sigdcp.model.identification.Role;
import ci.gouv.budget.solde.sigdcp.model.identification.Verrou.Cause;

public interface CompteUtilisateurDao extends DataAccessObject<CompteUtilisateur,Long> {

	CompteUtilisateur readByUsername(String username);
	
	CompteUtilisateur readByCredentials(Credentials credentials);
	
	CompteUtilisateur readByMatricule(String matricule);
	
	CompteUtilisateur readByEMail(String email);
	
	CompteUtilisateur readByCodeVerrouByCauseVerrou(String code,Cause cause);

	Collection<CompteUtilisateur> readByTypeByRole(Class<? extends Party> aClass,Role role);
	
	CompteUtilisateur readByCompteContribuable(Long cc);

	CompteUtilisateur readByParty(Party party);
}
