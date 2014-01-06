package ci.gouv.budget.solde.sigdcp.dao.identification;

import java.io.Serializable;

import ci.gouv.budget.solde.sigdcp.dao.JpaDaoImpl;
import ci.gouv.budget.solde.sigdcp.model.identification.CompteUtilisateur;

public class CompteUtilisateurDaoImpl extends JpaDaoImpl<CompteUtilisateur, String> implements CompteUtilisateurDao , Serializable {

	private static final long serialVersionUID = -2609724288199083806L;

	@Override
	public CompteUtilisateur findByUsernameByPassword(String username,String passworg) {
		// TODO
		return null;
	}
	
	
	
}