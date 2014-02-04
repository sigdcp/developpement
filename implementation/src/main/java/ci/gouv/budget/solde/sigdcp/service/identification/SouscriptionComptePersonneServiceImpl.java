package ci.gouv.budget.solde.sigdcp.service.identification;

import static ci.gouv.budget.solde.sigdcp.service.ServiceExceptionType.IDENTIFICATION_SOUSCRIPTION_COMPTE_ACCEPTE;
import static ci.gouv.budget.solde.sigdcp.service.ServiceExceptionType.IDENTIFICATION_SOUSCRIPTION_COMPTE_ENCOURS;
import static ci.gouv.budget.solde.sigdcp.service.ServiceExceptionType.IDENTIFICATION_SOUSCRIPTION_COMPTE_EXISTE;
import static ci.gouv.budget.solde.sigdcp.service.ServiceExceptionType.IDENTIFICATION_SOUSCRIPTION_COMPTE_INCOHERENT;
import static ci.gouv.budget.solde.sigdcp.service.ServiceExceptionType.IDENTIFICATION_SOUSCRIPTION_COMPTE_MAIL_EXISTE;
import static ci.gouv.budget.solde.sigdcp.service.ServiceExceptionType.IDENTIFICATION_SOUSCRIPTION_COMPTE_MATRCULE_INCONNU;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import ci.gouv.budget.solde.sigdcp.dao.identification.AgentEtatDao;
import ci.gouv.budget.solde.sigdcp.dao.identification.CompteUtilisateurDao;
import ci.gouv.budget.solde.sigdcp.dao.identification.SouscriptionComptePersonneDao;
import ci.gouv.budget.solde.sigdcp.model.Code;
import ci.gouv.budget.solde.sigdcp.model.MailMessage;
import ci.gouv.budget.solde.sigdcp.model.identification.AgentEtat;
import ci.gouv.budget.solde.sigdcp.model.identification.CompteUtilisateur;
import ci.gouv.budget.solde.sigdcp.model.identification.souscription.SouscriptionComptePersonne;
import ci.gouv.budget.solde.sigdcp.service.MailService;
import ci.gouv.budget.solde.sigdcp.service.ServiceException;
import ci.gouv.budget.solde.sigdcp.service.utils.validaton.SouscriptionComptePersonneValidator;

@Stateless
public class SouscriptionComptePersonneServiceImpl extends AbstractSouscriptionServiceImpl<SouscriptionComptePersonne> implements SouscriptionComptePersonneService,Serializable {
	
	private static final long serialVersionUID = 1170771216036513138L;

	@Inject private SouscriptionComptePersonneValidator validator;
	@Inject private SouscriptionComptePersonneDao souscriptionComptePersonneDao;
	@Inject private AgentEtatDao agentEtatDao;
	@Inject private CompteUtilisateurDao compteUtilisateurDao;
	@Inject private MailService mailService;
	
	/*
	@Inject private PersonneDao ayantDroitDao;
	@Inject private NotificationSystem notificationSystem;
	*/
	@Inject
	public SouscriptionComptePersonneServiceImpl(SouscriptionComptePersonneDao dao) {
		super(dao);
	}

	@Override
	public void souscrire(SouscriptionComptePersonne souscriptionComptePersonne) throws ServiceException {
		validator.init(souscriptionComptePersonne).validate();
		if(!validator.isSucces())
			serviceException(validator.getMessagesAsString());
		
		souscriptionComptePersonne.setPersonneReferencee(null);
		souscriptionComptePersonne.setCode(System.currentTimeMillis()+"");
		souscriptionComptePersonne.getPersonneDemandeur().getPersonne().setCode(System.currentTimeMillis()+"");
		// Est ce qu'il a déja une inscription en cours de validation ou acceptée
		SouscriptionComptePersonne souscriptionComptePersonneExistante = souscriptionComptePersonneDao.readByMatricule(souscriptionComptePersonne.getPersonneDemandeur().getMatricule());
		
		if(souscriptionComptePersonneExistante!=null){
			System.out
					.println("SouscriptionComptePersonneServiceImpl.souscrire()");
			if(souscriptionComptePersonneExistante.getDateValidation()==null)
				serviceException(IDENTIFICATION_SOUSCRIPTION_COMPTE_ENCOURS);
		
			if( Boolean.TRUE.equals(souscriptionComptePersonneExistante.getAcceptee()))
				serviceException(IDENTIFICATION_SOUSCRIPTION_COMPTE_ACCEPTE);
		}
		
		if(!Code.TYPE_AGENT_ETAT_GENDARME.equals(souscriptionComptePersonne.getPersonneDemandeur().getType().getCode())){
			//ce n'est pas un gendarme
			//Est ce qu'il est connu du système
			AgentEtat agentEtat = agentEtatDao.readByMatricule(souscriptionComptePersonne.getPersonneDemandeur().getMatricule());
			//System.out.println(agentEtat);
			if(agentEtat==null)
				serviceException(IDENTIFICATION_SOUSCRIPTION_COMPTE_MATRCULE_INCONNU);
			//Est ce qu'il à un compte
			CompteUtilisateur compteUtilisateur = compteUtilisateurDao.readByMatricule(agentEtat.getMatricule());
			if(compteUtilisateur!=null)
				serviceException(IDENTIFICATION_SOUSCRIPTION_COMPTE_EXISTE);
			//Est ce que ce compte n'est pas déja lié à l'adresse email
			compteUtilisateur = compteUtilisateurDao.readByMatricule(agentEtat.getMatricule());
			if(compteUtilisateur!=null)
				serviceException(IDENTIFICATION_SOUSCRIPTION_COMPTE_MAIL_EXISTE);
				
			if(!cohente(souscriptionComptePersonne))
				serviceException(IDENTIFICATION_SOUSCRIPTION_COMPTE_INCOHERENT);
			
			souscriptionComptePersonne.setDateCreation(new Date());
			souscriptionComptePersonne.setDateValidation(souscriptionComptePersonne.getDateCreation());
			souscriptionComptePersonne.setAcceptee(Boolean.TRUE);
			souscriptionComptePersonneDao.create(souscriptionComptePersonne);
			compteUtilisateur = new CompteUtilisateur();
			compteUtilisateur.setUtilisateur(agentEtat);
			compteUtilisateur.getCredentials().setUsername(souscriptionComptePersonne.getPersonneDemandeur().getPersonne().getContact().getEmail());
			compteUtilisateur.getCredentials().setPassword(souscriptionComptePersonne.getPassword());
			compteUtilisateurDao.create(compteUtilisateur);
			
			mailService.send(new MailMessage("Souscription SIGDCP", "Votre compte a ete creer avec succes<br/>Votre login est : "+compteUtilisateur.getCredentials().getUsername()), agentEtat.getContact().getEmail());
			
		}else{
			//c'est un gendarme
			souscriptionComptePersonne.setDateCreation(new Date());
			souscriptionComptePersonneDao.create(souscriptionComptePersonne);
			mailService.send(new MailMessage("Souscription SIGDCP", "Votre sousciption a ete creer avec succes. Elle sera traitee"),souscriptionComptePersonne.getPersonneDemandeur().getPersonne().getContact().getEmail());
		}
		
	}
	
	private boolean cohente(SouscriptionComptePersonne souscriptionComptePersonne){
		return true;
	}

	@Override
	public void accepter(SouscriptionComptePersonne souscriptionCompte)throws ServiceException {
		dao.update(souscriptionCompte);
	} 

	@Override
	public void accepter(Collection<SouscriptionComptePersonne> souscriptionComptes) throws ServiceException {
		for(SouscriptionComptePersonne souscriptionCompte : souscriptionComptes)
			accepter(souscriptionCompte);
	}
	
	@Override
	public void rejeter(SouscriptionComptePersonne souscriptionCompte) throws ServiceException {
		souscriptionCompte.setDateValidation(new Date());
		souscriptionCompte.setAcceptee(Boolean.FALSE);
		dao.update(souscriptionCompte);
	}
	
	@Override
	public void rejeter(Collection<SouscriptionComptePersonne> souscriptionComptes) throws ServiceException {
		for(SouscriptionComptePersonne souscriptionCompte : souscriptionComptes)
			rejeter(souscriptionCompte);
	}
	
	/*
	 * Read only services
	 */

	@Override @Transactional(value=TxType.NEVER)
	public Collection<SouscriptionComptePersonne> findSouscriptionsAValiderByTypePersonneId(String typePersonneId) {
		return ((SouscriptionComptePersonneDao)dao).findByDateValidationIsNullByTypePersonneId(typePersonneId);
	}
	
	@Override @Transactional(value=TxType.NEVER)
	public Collection<SouscriptionComptePersonne> findSouscriptionsAValider() {
		return ((SouscriptionComptePersonneDao)dao).findByDateValidationIsNull();
	}

}
