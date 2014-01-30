package ci.gouv.budget.solde.sigdcp.dao.identification;

import java.io.Serializable;

import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;

import ci.gouv.budget.solde.sigdcp.dao.JpaDaoImpl;
import ci.gouv.budget.solde.sigdcp.model.identification.CompteUtilisateur;

public class CompteUtilisateurDaoImpl extends JpaDaoImpl<CompteUtilisateur, Long> implements CompteUtilisateurDao , Serializable {

	private static final long serialVersionUID = -2609724288199083806L;

	@Override
	public CompteUtilisateur findByUsernameByPassword(String username,String passworg) {
		// TODO
		return null;
	}
	
	@Override
	public CompteUtilisateur readByMatricule(String matricule) {
		try {
			return entityManager.createQuery("SELECT cu FROM CompteUtilisateur cu WHERE TYPE(cu.personne) IN AgentEtat AND cu.personne.matricule = :matricule", clazz)
					.setParameter("matricule", matricule)
					.getSingleResult();
		} catch (NoResultException e) {
			return null;
		} catch (NonUniqueResultException e) {
			throw e;
		} 
	}
	
}