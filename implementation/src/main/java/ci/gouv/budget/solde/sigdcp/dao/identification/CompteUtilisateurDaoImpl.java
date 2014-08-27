package ci.gouv.budget.solde.sigdcp.dao.identification;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;

import ci.gouv.budget.solde.sigdcp.dao.JpaDaoImpl;
import ci.gouv.budget.solde.sigdcp.model.identification.CompteUtilisateur;
import ci.gouv.budget.solde.sigdcp.model.identification.Credentials;
import ci.gouv.budget.solde.sigdcp.model.identification.Party;
import ci.gouv.budget.solde.sigdcp.model.identification.Role;
import ci.gouv.budget.solde.sigdcp.model.identification.Verrou.Cause;

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
	
	@Override
	public CompteUtilisateur readByUsername(String username) {
		try {
			return entityManager.createQuery("SELECT cu FROM CompteUtilisateur cu WHERE cu.credentials.username = :username", clazz)
					.setParameter("username", username)
					.getSingleResult();
		} catch (NoResultException e) {
			return null;
		} catch (NonUniqueResultException e) {
			throw e;
		} 
	}
	
	@Override
	public CompteUtilisateur readByCredentials(Credentials credentials) {
		try {
			return entityManager.createQuery("SELECT cu FROM CompteUtilisateur cu WHERE cu.credentials = :credentials", clazz)
					.setParameter("credentials", credentials)
					.getSingleResult();
		} catch (NoResultException e) {
			return null;
		} catch (NonUniqueResultException e) {
			throw e;
		} 
	}
	
	@Override
	public CompteUtilisateur readByCodeVerrouByCauseVerrou(String code,Cause cause) {
		try {
			return entityManager.createQuery("SELECT cu FROM CompteUtilisateur cu WHERE cu.verrou.code = :code AND cu.verrou.cause = :cause", clazz)
					.setParameter("code", code)
					.setParameter("cause", cause)
					.getSingleResult();
		} catch (NoResultException e) {
			return null;
		} catch (NonUniqueResultException e) {
			throw e;
		} 
	}

	@Override
	public Collection<CompteUtilisateur> readByTypeByRole(Class<? extends Party> aClass,Role role) {
		return entityManager.createQuery("SELECT cu FROM CompteUtilisateur cu WHERE TYPE(cu.utilisateur) = :aClass AND :role MEMBER OF cu.roles", clazz)
				.setParameter("role", role)
				.setParameter("aClass", aClass)
				.getResultList();
	}
	
	@Override
	public CompteUtilisateur readByCompteContribuable(Long cc) {
		try {
			return entityManager.createQuery("SELECT cu FROM CompteUtilisateur cu WHERE cu.utilisateur.compteContribuable = :compteContribuable", clazz)
					.setParameter("compteContribuable", cc)
					.getSingleResult();
		} catch (NoResultException e) {
			return null;
		} catch (NonUniqueResultException e) {
			throw e;
		} 
	}
	
	@Override
	public CompteUtilisateur readByParty(Party party) {
		try {
			return entityManager.createQuery("SELECT cu FROM CompteUtilisateur cu WHERE cu.utilisateur = :utilisateur", clazz)
					.setParameter("utilisateur", party)
					.getSingleResult();
		} catch (NoResultException e) {
			return null;
		} catch (NonUniqueResultException e) {
			throw e;
		} 
	}
	
}