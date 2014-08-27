package ci.gouv.budget.solde.sigdcp.service.identification;

import static ci.gouv.budget.solde.sigdcp.service.ServiceExceptionType.*;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

import javax.ejb.Stateless;
import javax.inject.Inject;

import ci.gouv.budget.solde.sigdcp.dao.identification.CompteUtilisateurDao;
import ci.gouv.budget.solde.sigdcp.dao.identification.PrestataireReferenceDao;
import ci.gouv.budget.solde.sigdcp.dao.identification.SouscriptionComptePrestataireDao;
import ci.gouv.budget.solde.sigdcp.dao.prestation.PrestataireDao;
import ci.gouv.budget.solde.sigdcp.model.Code;
import ci.gouv.budget.solde.sigdcp.model.communication.NotificationMessageType;
import ci.gouv.budget.solde.sigdcp.model.geographie.Contact;
import ci.gouv.budget.solde.sigdcp.model.identification.CompteUtilisateur;
import ci.gouv.budget.solde.sigdcp.model.identification.ReponseSecrete;
import ci.gouv.budget.solde.sigdcp.model.identification.Role;
import ci.gouv.budget.solde.sigdcp.model.identification.souscription.InfosSouscriptionComptePrestataire;
import ci.gouv.budget.solde.sigdcp.model.identification.souscription.SouscriptionComptePrestataire;
import ci.gouv.budget.solde.sigdcp.model.prestation.Prestataire;
import ci.gouv.budget.solde.sigdcp.service.ServiceException;
import ci.gouv.budget.solde.sigdcp.service.utils.validaton.SouscriptionComptePrestataireValidator;

@Stateless
public class SouscriptionComptePrestataireServiceImpl extends AbstractSouscriptionServiceImpl<SouscriptionComptePrestataire> implements SouscriptionComptePrestataireService,Serializable {
	
	private static final long serialVersionUID = 1170771216036513138L;

	@Inject private SouscriptionComptePrestataireValidator validator;
	@Inject private PrestataireReferenceDao prestataireReferenceDao;
	@Inject private SouscriptionComptePrestataireDao souscriptionComptePrestataireDao;
	@Inject private PrestataireDao prestataireDao;
	@Inject private CompteUtilisateurDao compteUtilisateurDao;
	
	@Inject
	public SouscriptionComptePrestataireServiceImpl(SouscriptionComptePrestataireDao dao) {
		super(dao); 
	}

	@Override
	public void souscrire(SouscriptionComptePrestataire souscriptionComptePrestataire) throws ServiceException {
		if(!validator.validate(souscriptionComptePrestataire).isSucces())
			serviceException(validator.getMessagesAsString());
		
		CompteUtilisateur compteUtilisateur;
		
		if(souscriptionComptePrestataire.getPrestataireDemandeur().getType()==null){
			Prestataire souscripteur = souscriptionComptePrestataire.getPrestataireDemandeur().getPrestataire();
			createSouscription(souscriptionComptePrestataire);
			
			souscriptionComptePrestataire.setDateValidation(souscriptionComptePrestataire.getDateCreation());
			souscriptionComptePrestataire.setAcceptee(Boolean.TRUE);
			
			compteUtilisateur = createCompteUtilisteur(souscriptionComptePrestataire,souscripteur,Code.ROLE_PRESTATAIRE);
			
			notifier(NotificationMessageType.AVIS_SOUSCRIPTION_COMPTE_PRESTATAIRE,new Object[]{
					"loginUtilisateur",compteUtilisateur.getCredentials().getUsername(),"motPasseUtilisateur",compteUtilisateur.getCredentials().getPassword(),
					"nomPrenomsAgentEtat",souscripteur.getNom()
			},souscripteur);
			return;
		}
		
		String id = souscriptionComptePrestataire.getPrestataireDemandeur().getId()+"";
		// Est ce qu'il a déja une inscription en cours de validation ou acceptée
		SouscriptionComptePrestataire souscriptionComptePrestataireExistante = souscriptionComptePrestataireDao.read(id);
		
		if(souscriptionComptePrestataireExistante!=null){			
			if(souscriptionComptePrestataireExistante.getDateValidation()==null)
				serviceException(IDENTIFICATION_SOUSCRIPTION_COMPTE_ENCOURS);
		
			if( Boolean.TRUE.equals(souscriptionComptePrestataireExistante.getAcceptee()))
				serviceException(IDENTIFICATION_SOUSCRIPTION_COMPTE_ACCEPTE);
		}
		
		
		Prestataire pt = souscriptionComptePrestataire.getPrestataireDemandeur().getPrestataire();
			
		//Est ce qu'il est dans la table de référence
			 if(!prestataireReferenceDao.exist(pt.getCompteContribuable()))
				serviceException(IDENTIFICATION_SOUSCRIPTION_COMPTE_CC_INCONNU);
			//Est ce qu'il à un compte
			 compteUtilisateur = compteUtilisateurDao.readByCompteContribuable(pt.getCompteContribuable());
			if(compteUtilisateur!=null)
				serviceException(IDENTIFICATION_SOUSCRIPTION_COMPTE_EXISTE);
			//Est ce que ce compte n'est pas déja lié à l'adresse email
			compteUtilisateur = compteUtilisateurDao.readByEMail(souscriptionComptePrestataire.getPrestataireDemandeur().getPrestataire().getContact().getEmail());
			if(compteUtilisateur!=null)
				serviceException(IDENTIFICATION_SOUSCRIPTION_COMPTE_MAIL_EXISTE);
			
			
			createSouscription(souscriptionComptePrestataire);
			souscriptionComptePrestataire.setDateValidation(souscriptionComptePrestataire.getDateCreation());
			souscriptionComptePrestataire.setAcceptee(Boolean.TRUE);
			
			Prestataire prestataire = createPrestataire(souscriptionComptePrestataire);
			compteUtilisateur = createCompteUtilisteur(souscriptionComptePrestataire,prestataire,Code.ROLE_PRESTATAIRE);
			notifier(NotificationMessageType.AVIS_SOUSCRIPTION_COMPTE_PERSONNE_FONCTIONNAIRE,new Object[]{
					"loginUtilisateur",compteUtilisateur.getCredentials().getUsername(),"motPasseUtilisateur",compteUtilisateur.getCredentials().getPassword(),
					"nomPrenomsAgentEtat",prestataire.getNom()
			},prestataire);
			
		
	}
	
	private Prestataire createPrestataire(SouscriptionComptePrestataire souscriptionComptePrestataire){
		InfosSouscriptionComptePrestataire d = souscriptionComptePrestataire.getPrestataireDemandeur();
		Prestataire p = d.getPrestataire();
	
		Prestataire prestataire = new Prestataire(p.getSigle(), p.getSiege(), p.getCompteContribuable(), d.getType(), p.getNom(), new Contact(p.getContact()));
		
		prestataireDao.create(prestataire);
		return prestataire;
	}
	
	/**
	 * Initialisations des attributs et persistence
	 * @param souscriptionComptePrestataire
	 */
	private void createSouscription(SouscriptionComptePrestataire souscriptionComptePrestataire){
		souscriptionComptePrestataire.setPrestataireReferencee(null);
		souscriptionComptePrestataire.setCode(System.currentTimeMillis()+"");
		souscriptionComptePrestataire.setDateCreation(tempsCourant());
		souscriptionComptePrestataireDao.create(souscriptionComptePrestataire);
	}
	
	/**
	 * Initialisations des attributs et persistence
	 * 
	 */
	private CompteUtilisateur createCompteUtilisteur(SouscriptionComptePrestataire souscriptionComptePrestataire,Prestataire prestataire,String codeRole){
		CompteUtilisateur compteUtilisateur = new CompteUtilisateur();
		compteUtilisateur.setDateCreation(new Date());
		compteUtilisateur.setUtilisateur(prestataire);
		compteUtilisateur.getCredentials().setUsername(souscriptionComptePrestataire.getPrestataireDemandeur().getPrestataire().getContact().getEmail());
		compteUtilisateur.getCredentials().setPassword(souscriptionComptePrestataire.getPassword());
		compteUtilisateur.getRoles().add(genericDao.readByClass(Role.class, codeRole));
		for(ReponseSecrete reponseSecrete : souscriptionComptePrestataire.getReponseSecretes())
			compteUtilisateur.getReponseSecretes().add(reponseSecrete);
		
		compteUtilisateurDao.create(compteUtilisateur);
		return compteUtilisateur;
	}

	@Override
	public void accepter(SouscriptionComptePrestataire souscriptionCompte)throws ServiceException {
		dao.update(souscriptionCompte);
	} 

	@Override
	public void accepter(Collection<SouscriptionComptePrestataire> souscriptionComptes) throws ServiceException {
		for(SouscriptionComptePrestataire souscriptionCompte : souscriptionComptes)
			accepter(souscriptionCompte);
	}
	
	@Override
	public void rejeter(SouscriptionComptePrestataire souscriptionCompte) throws ServiceException {
		souscriptionCompte.setDateValidation(new Date());
		souscriptionCompte.setAcceptee(Boolean.FALSE);
		dao.update(souscriptionCompte);
	}
	
	@Override
	public void rejeter(Collection<SouscriptionComptePrestataire> souscriptionComptes) throws ServiceException {
		for(SouscriptionComptePrestataire souscriptionCompte : souscriptionComptes)
			rejeter(souscriptionCompte);
	}
	
	/*
	 * Read only services
	 */

	@Override
	public Collection<SouscriptionComptePrestataire> findSouscriptionsAValiderByTypePrestataireId(String typePrestataireId) {
		return ((SouscriptionComptePrestataireDao)dao).readDateValidationIsNullByTypePrestataireId(typePrestataireId);
	} 
	
	@Override
	public Collection<SouscriptionComptePrestataire> findSouscriptionsAValider() {
		return ((SouscriptionComptePrestataireDao)dao).readDateValidationIsNull();
	}

}
