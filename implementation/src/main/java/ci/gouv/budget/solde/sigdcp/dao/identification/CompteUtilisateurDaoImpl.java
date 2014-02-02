package ci.gouv.budget.solde.sigdcp.dao.identification;

import java.io.Serializable;

import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;

import ci.gouv.budget.solde.sigdcp.dao.JpaDaoImpl;
import ci.gouv.budget.solde.sigdcp.model.identification.CompteUtilisateur;

public class CompteUtilisateurDaoImpl extends JpaDaoImpl<CompteUtilisateur, Long> implements CompteUtilisateurDao , Serializable {

	private static final long serialVersionUID = -2609724288199083806L;

	@Override
	public CompteUtilisateur readByMatricule(String matricule) {
		try {
			return entityManager.createQuery("SELECT cu FROM CompteUtilisateur cu WHERE EXISTS"
					+ " ( SELECT ae FROM AgentEtat ae WHERE ae = cu.utilisateur AND ae.matricule = :matricule)", clazz)
					.setParameter("matricule", matricule)
					.getSingleResult();
		} catch (NoResultException e) {
			return null;
		} catch (NonUniqueResultException e) {
			throw e;
		} 
	}
	
	@Override
	public CompteUtilisateur readByEMail(String email) {
		try {
			return entityManager.createQuery("SELECT cu FROM CompteUtilisateur cu WHERE cu.utilisateur.contact.email = :email", clazz)
					.setParameter("email", email)
					.getSingleResult();
		} catch (NoResultException e) {
			return null;
		} catch (NonUniqueResultException e) {
			throw e;
		} 
	}
	
}