package ci.gouv.budget.solde.sigdcp.service.identification;

import java.util.Collection;

import ci.gouv.budget.solde.sigdcp.model.identification.AgentEtat;
import ci.gouv.budget.solde.sigdcp.model.identification.CompteUtilisateur;
import ci.gouv.budget.solde.sigdcp.model.identification.Credentials;
import ci.gouv.budget.solde.sigdcp.model.identification.DelegueSotra;
import ci.gouv.budget.solde.sigdcp.model.identification.Party;
import ci.gouv.budget.solde.sigdcp.model.identification.ReponseSecrete;
import ci.gouv.budget.solde.sigdcp.model.identification.Role;
import ci.gouv.budget.solde.sigdcp.model.identification.Verrou;
import ci.gouv.budget.solde.sigdcp.model.identification.Verrou.Cause;
import ci.gouv.budget.solde.sigdcp.service.AbstractService;
import ci.gouv.budget.solde.sigdcp.service.ServiceException;

public interface CompteUtilisateurService extends AbstractService<CompteUtilisateur,Long> {

	/**
	 * Authentifie un compte auprès du système
	 * @param username
	 * @param password
	 * @return
	 * @throws ServiceException
	 */
	CompteUtilisateur authentifier(Credentials credentials) throws ServiceException ;
	
	/**
	 * Deconnecte un compte du système
	 * @param compteUtilisateur
	 * @throws ServiceException
	 */
	void deconnecter(CompteUtilisateur compteUtilisateur) throws ServiceException ;
	
	void verouiller(CompteUtilisateur compteUtilisateur,Cause causeDeverouillage) throws ServiceException ;
	
	void deverouiller(Verrou verrou,Credentials credentials) throws ServiceException ;
	
	
	
	/**
	 * Procedure de recuperation de mot de passe : Verifie l'identité de l'agent de l'état et ramene ses questions
	 * secrètes 
	 * @param agentEtat
	 * @return
	 * @throws ServiceException
	 */
	Collection<ReponseSecrete> recupererPasswordEtape1(AgentEtat agentEtat) throws ServiceException;
	
	/**
	 * Procedure de recuperation de mot de passe : Verifie la justesse des reponses et initie la procedure de reinitialisation
	 * du mot de passe
	 * @param agentEtat
	 * @param reponses
	 * @throws ServiceException
	 */
	void recupererPasswordEtape2(AgentEtat agentEtat, Collection<ReponseSecrete> reponses) throws ServiceException;
	
	void deverouillable(Verrou verrou) throws ServiceException;
	
	void modifierRoles(CompteUtilisateur compteUtilisateur,Collection<Role> nouveauxRoles,DelegueSotra delegueSotra) throws ServiceException ;
	
	CompteUtilisateur findByCredentials(Credentials credentials);
	
	Collection<CompteUtilisateur> findByTypeByRole(Class<? extends Party> aClass,Role role);
	
	CompteUtilisateur findByParty(Party party);
	
}
