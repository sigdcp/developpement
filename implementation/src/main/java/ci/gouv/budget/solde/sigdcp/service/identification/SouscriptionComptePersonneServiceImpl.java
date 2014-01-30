package ci.gouv.budget.solde.sigdcp.service.identification;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

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

@Stateless
public class SouscriptionComptePersonneServiceImpl extends AbstractSouscriptionServiceImpl<SouscriptionComptePersonne> implements SouscriptionComptePersonneService,Serializable {
	
	private static final long serialVersionUID = 1170771216036513138L;

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
		souscriptionComptePersonne.setPersonneReferencee(null);
		souscriptionComptePersonne.setCode(System.currentTimeMillis()+"");
		souscriptionComptePersonne.getPersonneDemandeur().getPersonne().setCode(System.currentTimeMillis()+"");
		// Est ce qu'il a déja une inscription en cours de validation ou acceptée
		SouscriptionComptePersonne souscriptionComptePersonneExistante = souscriptionComptePersonneDao.readByMatricule(souscriptionComptePersonne.getPersonneDemandeur().getMatricule());
		
		if(souscriptionComptePersonneExistante!=null){
			if(souscriptionComptePersonneExistante.getDateValidation()==null)
				throw new ServiceException("Vous avez une souscription déja en cours de validation");
		
			if( Boolean.TRUE.equals(souscriptionComptePersonneExistante.getAcceptee()))
				throw new ServiceException("Vous avez déja souscrit");
		}
		
		if(!Code.TYPE_AGENT_ETAT_GENDARME.equals(souscriptionComptePersonne.getPersonneDemandeur().getType().getCode())){
			//ce n'est pas un gendarme
			//Est ce qu'il est connu du système
			AgentEtat agentEtat = agentEtatDao.readByMatricule(souscriptionComptePersonne.getPersonneDemandeur().getMatricule());
			if(agentEtat==null)
				throw new ServiceException("Votre matricule est inconnu");
			//Est ce qu'il à un compte
			CompteUtilisateur compteUtilisateur = compteUtilisateurDao.readByMatricule(agentEtat.getMatricule());
			if(compteUtilisateur!=null)
				throw new ServiceException("Vous avez deja un compte");
			if(!cohente(souscriptionComptePersonne))
				throw new ServiceException("Les informations saisies ne sont pas cohérentes");
			
			souscriptionComptePersonne.setDateCreation(new Date());
			souscriptionComptePersonneDao.create(souscriptionComptePersonne);
			compteUtilisateur = new CompteUtilisateur();
			compteUtilisateur.getCredentials().setUsername(souscriptionComptePersonne.getPersonneDemandeur().getPersonne().getContact().getEmail());
			compteUtilisateur.getCredentials().setPassword(souscriptionComptePersonne.getMotPasse());
			compteUtilisateurDao.create(compteUtilisateur);
			
			mailService.send(new MailMessage("Vous venez de créer un compte", "Login : "+compteUtilisateur.getCredentials().getUsername()), agentEtat.getContact().getEmail());
			
		}else{
			//c'est un gendarme
			souscriptionComptePersonne.setDateCreation(new Date());
			souscriptionComptePersonneDao.create(souscriptionComptePersonne);
			mailService.send(new MailMessage("Vous serez information de la suite", ""),souscriptionComptePersonne.getPersonneDemandeur().getPersonne().getContact().getEmail());
		}
		
		//ServiceUtils.throwNotYetImplemented();
		//inscription.setCode(System.currentTimeMillis()+"");
		/*switch(inscription.getType()){
		
		case BENEFICIAIRE:
			inscription.setAyantDroitInfos(null);
			break;
		case AYANT_DROIT:
			
			break;
		}
		*/
		//inscription.setDateCreation(new Date());
		//dao.create(inscription);
	}
	
	private boolean cohente(SouscriptionComptePersonne souscriptionComptePersonne){
		return true;
	}

	@Override
	public void accepter(SouscriptionComptePersonne souscriptionCompte)throws ServiceException {
		/*Beneficiaire beneficiaire;
		inscription.setDateValidation(new Date());
		inscription.setAccepte(Boolean.TRUE);*/
		/*switch(inscription.getType()){
		
		case BENEFICIAIRE:
			beneficiaire = createBeneficiaireFrom(inscription);
			beneficiaireDao.create(beneficiaire);
			//envoi de notification par mail/sms... au beneficiaire
			notificationSystem.notify(beneficiaire);
			break;
		case AYANT_DROIT:
			AyantDroit ayantDroit = createAyantDroitFrom(inscription);
			ayantDroitDao.create(ayantDroit);
			beneficiaire = beneficiaireDao.findByMatricule(inscription.getMatricule());// est ce que le matricule existe ds la liste des beneficiaires ?
			if(beneficiaire==null){
				beneficiaire = createBeneficiaireFrom(inscription);
				beneficiaireDao.create(beneficiaire);
			}
			//lien entre ayant droit et le beneficiaire
			beneficiaire.setAyantDroit(ayantDroit);
			beneficiaireDao.update(beneficiaire);
			//envoi de notification par mail/sms... a l'ayant droit
			notificationSystem.notify(ayantDroit);
			break;
		default: throw new ServiceException("Ce type d'inscription n'est pas supporté");
		}*/
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

	@Override
	public Collection<SouscriptionComptePersonne> findSouscriptionsAValiderByTypePersonneId(String typePersonneId) {
		return ((SouscriptionComptePersonneDao)dao).findByDateValidationIsNullByTypePersonneId(typePersonneId);
	}
	
	@Override
	public Collection<SouscriptionComptePersonne> findSouscriptionsAValider() {
		return ((SouscriptionComptePersonneDao)dao).findByDateValidationIsNull();
	}
	
	/*
	private Beneficiaire createBeneficiaireFrom(Inscription inscription){
		InfosInscriptionPersonne b = inscription.getBeneficiaireInfos();
		Beneficiaire beneficiaire = new Beneficiaire(System.currentTimeMillis()+"",b.getNom(),b.getPrenoms(),b.getDateNaissance(),b.getContact(),b.getSexe(),inscription.getSituationMatrimoniale(),
				new Date(),inscription.getMatricule(),inscription.getGrade(),inscription.getEchelon(),inscription.getPosition(),inscription.getIndice(),inscription.getFonction(),
				inscription.getMinistere(),inscription.getProfession(),inscription.getCategorie(),null,b.getExpatrie());
		return beneficiaire;
	}
	
	private AyantDroit createAyantDroitFrom(Inscription inscription){
		InfosInscriptionPersonne b = inscription.getAyantDroitInfos();
		AyantDroit ayantDroit = new AyantDroit(System.currentTimeMillis()+"", b.getNom(), b.getPrenoms(), b.getDateNaissance(), b.getContact(), b.getSexe(), null, new Date());
		return ayantDroit;
	}
	*/
	
	
	

}
