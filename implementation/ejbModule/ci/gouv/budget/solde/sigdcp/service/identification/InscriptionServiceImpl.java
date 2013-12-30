package ci.gouv.budget.solde.sigdcp.service.identification;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

import javax.ejb.Stateless;
import javax.inject.Inject;

import ci.gouv.budget.solde.sigdcp.dao.identification.AgentEtatDao;
import ci.gouv.budget.solde.sigdcp.dao.identification.InscriptionDao;
import ci.gouv.budget.solde.sigdcp.dao.identification.PersonneDao;
import ci.gouv.budget.solde.sigdcp.model.identification.Inscription;
import ci.gouv.budget.solde.sigdcp.service.DefaultServiceImpl;
import ci.gouv.budget.solde.sigdcp.service.NotificationSystem;
import ci.gouv.budget.solde.sigdcp.service.ServiceException;
import ci.gouv.budget.solde.sigdcp.service.utils.ServiceUtils;

@Stateless
public class InscriptionServiceImpl extends DefaultServiceImpl<Inscription, String> implements InscriptionService,Serializable {
	
	private static final long serialVersionUID = 1170771216036513138L;

	@Inject private AgentEtatDao beneficiaireDao;
	@Inject private PersonneDao ayantDroitDao;
	@Inject private NotificationSystem notificationSystem;
	
	@Inject
	public InscriptionServiceImpl(InscriptionDao dao) {
		super(dao);
	}

	@Override
	public void inscrire(Inscription inscription) throws ServiceException {
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

	@Override
	public void accepterInscription(Inscription inscription)throws ServiceException {
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
		dao.update(inscription);
	} 

	@Override
	public void accepterInscription(Collection<Inscription> inscriptions) throws ServiceException {
		for(Inscription inscription : inscriptions)
			accepterInscription(inscription);
	}
	
	@Override
	public void rejeterInscription(Inscription inscription) throws ServiceException {
		inscription.setDateValidation(new Date());
		inscription.setAccepte(Boolean.FALSE);
		dao.update(inscription);
	}
	
	@Override
	public void rejeterInscription(Collection<Inscription> inscriptions) throws ServiceException {
		for(Inscription inscription : inscriptions)
			rejeterInscription(inscription);
	}

	@Override
	public Collection<Inscription> findInscriptionsAValiderByTypePersonneId(String typePersonneId) {
		return ((InscriptionDao)dao).findByDateValidationIsNullByTypePersonneId(typePersonneId);
	}
	
	@Override
	public Collection<Inscription> findInscriptionsAValider() {
		return ((InscriptionDao)dao).findByDateValidationIsNull();
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
