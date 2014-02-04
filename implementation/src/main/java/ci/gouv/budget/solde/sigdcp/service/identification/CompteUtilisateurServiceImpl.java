package ci.gouv.budget.solde.sigdcp.service.identification;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;

import javax.ejb.Stateless;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.ServletRequest;

import org.apache.commons.lang3.RandomStringUtils;

import ci.gouv.budget.solde.sigdcp.dao.identification.AgentEtatDao;
import ci.gouv.budget.solde.sigdcp.dao.identification.CompteUtilisateurDao;
import ci.gouv.budget.solde.sigdcp.model.MailMessage;
import ci.gouv.budget.solde.sigdcp.model.identification.AgentEtat;
import ci.gouv.budget.solde.sigdcp.model.identification.CompteUtilisateur;
import ci.gouv.budget.solde.sigdcp.model.identification.Credentials;
import ci.gouv.budget.solde.sigdcp.model.identification.ReponseSecrete;
import ci.gouv.budget.solde.sigdcp.model.identification.Verrou;
import ci.gouv.budget.solde.sigdcp.model.identification.Verrou.Cause;
import ci.gouv.budget.solde.sigdcp.service.DefaultServiceImpl;
import ci.gouv.budget.solde.sigdcp.service.MailService;
import ci.gouv.budget.solde.sigdcp.service.ServiceException;
import ci.gouv.budget.solde.sigdcp.service.ServiceExceptionType;

@Stateless
public class CompteUtilisateurServiceImpl extends DefaultServiceImpl<CompteUtilisateur, Long> implements CompteUtilisateurService {

	private static final long serialVersionUID = 159214876975685747L;
	
	private static final Integer MAX_TENTATIVE_AUTH = 3;
	
	@Inject private AuthentificationInfos infos;
	@Inject private MailService mailService;
	@Inject private AgentEtatDao agentEtatDao;
	
	@Inject
	public CompteUtilisateurServiceImpl(CompteUtilisateurDao dao) {
		super(dao);
	}

	@Override
	public CompteUtilisateur authentifier(Credentials credentials) throws ServiceException {
		//if(infos.getTimestampDebut()!=null)
		//	serviceException(ServiceExceptionType.IDENTIFICATION_COMPTE_UTILISATEUR_CONNECTE);
		
		CompteUtilisateur compteUtilisateur = ((CompteUtilisateurDao)dao).readByUsername(credentials.getUsername());// readByCredentials(credentials);
		Boolean verouille = compteUtilisateur!=null && compteUtilisateur.getVerrou()!=null /*&& isValidTokenDeverouillage(compteUtilisateur.getTokenDeverouillage())*/;
		
		if(compteUtilisateur==null)//aucun compte avec ce username a été trouvé
			serviceException(ServiceExceptionType.IDENTIFICATION_COMPTE_UTILISATEUR_INCONNU);
		
		//System.out.println("Found token : "+ToStringBuilder.reflectionToString(compteUtilisateur,ToStringStyle.MULTI_LINE_STYLE));
		//dao.create(new CompteUtilisateur(new Credentials(RandomStringUtils.randomAlphabetic(4), RandomStringUtils.randomAlphabetic(4)), compteUtilisateur.getUtilisateur(), null));
		//for(CompteUtilisateur c : ((CompteUtilisateurDao)dao).readAll())
		//	System.out.println(c);
		 
		//un compte avec ce username a été trouvé
		infos.setNombreTentative(infos.getNombreTentative()+1);
		//System.out.println("CompteUtilisateurServiceImpl.authentifier() "+infos.getNombreTentative());
		if(!compteUtilisateur.getCredentials().equals(credentials)){//le mot de passe ne correspond pas
			if(!verouille && infos.getNombreTentative() == MAX_TENTATIVE_AUTH){
				verouiller(compteUtilisateur,Cause.ACCESS_MULTIPLE);
				serviceException(ServiceExceptionType.IDENTIFICATION_COMPTE_UTILISATEUR_INCONNU,Boolean.FALSE);//we do not roll back transaction
			}else
				serviceException(ServiceExceptionType.IDENTIFICATION_COMPTE_UTILISATEUR_INCONNU);
		}
		
		if(verouille)
			serviceException(ServiceExceptionType.IDENTIFICATION_COMPTE_UTILISATEUR_VEROUILLE);
		
		infos.setTimestampDebut(System.currentTimeMillis());
		
		return compteUtilisateur;
	}
	
	@Override
	public void deconnecter(CompteUtilisateur compteUtilisateur) throws ServiceException {
		if(compteUtilisateur==null)
			return;
		
		mailService.send(new MailMessage("Compte SIGDCP", "Vous vous êtes connecté le "+new Date(infos.getTimestampDebut())+" et déconnecté le "+new Date()), compteUtilisateur.getUtilisateur().getContact().getEmail());
		infos.clear();
	}
	
	@Override
	public void verouiller(CompteUtilisateur compteUtilisateur,Cause causeVerrouillage) throws ServiceException {
		compteUtilisateur.setVerrou(new Verrou(RandomStringUtils.randomAlphanumeric(64), causeVerrouillage, new Date(), RandomStringUtils.randomAlphanumeric(32)));
		dao.update(compteUtilisateur);
		//envoi du mail
		ServletRequest request = (ServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		String lien = null;
		String codeVerrou = null;
		try{
			codeVerrou = URLEncoder.encode(compteUtilisateur.getVerrou().getCode(),"UTF-8");
		}catch(UnsupportedEncodingException e){
			e.printStackTrace();
		}
		switch(causeVerrouillage){
		case ACCESS_MULTIPLE:
			lien = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getServletContext().getContextPath()+"/public/deverouillage.jsf?"+
					 constantResources.getWebRequestParamCodeDeverouillage()+"="+codeVerrou;
			mailService.send(new MailMessage("Compte SIGDCP", "Votre compte a été vérouillé. Votre code de dévérouillage est : "+compteUtilisateur.getVerrou().getJeton()+
					"Cliquez sur ce lien et suivez les insctructions pour dévérouiller votre compte. "+lien)
			, compteUtilisateur.getUtilisateur().getContact().getEmail());
			
			break;
			
		case REINITIALISATION_PASSWORD:
			lien = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getServletContext().getContextPath()+"/public/reinitialiserpassword.jsf"+
					constantResources.getWebRequestParamCodeDeverouillage()+"="+codeVerrou;
			mailService.send(new MailMessage("Compte SIGDCP", "Votre compte a été vérouillé. Votre code de dévérouillage est : "+compteUtilisateur.getVerrou().getJeton()+
					"Cliquez sur ce lien et suivez les insctructions pour réinitialiser votre mot de passe. "+lien)
			, compteUtilisateur.getUtilisateur().getContact().getEmail());
			
			break;
		}
	}
		
	@Override
	public void deverouiller(Verrou verrou,Credentials credentials) throws ServiceException {
		CompteUtilisateur compteUtilisateur = ((CompteUtilisateurDao)dao).readByCredentials(credentials);
		if(compteUtilisateur==null)
			serviceException(ServiceExceptionType.IDENTIFICATION_COMPTE_UTILISATEUR_INEXISTANT);
		
		//est ce que le compte est verouille
		if(compteUtilisateur.getVerrou()==null)
			serviceException(ServiceExceptionType.IDENTIFICATION_COMPTE_UTILISATEUR_ACTIF);
		
		if(!compteUtilisateur.getVerrou().getCode().equals(verrou.getCode()))
			serviceException(ServiceExceptionType.IDENTIFICATION_COMPTE_UTILISATEUR_CODE_DEVEROUILLAGE_INCONNU);
		
		if(!compteUtilisateur.getVerrou().getJeton().equals(verrou.getJeton()))
			serviceException(ServiceExceptionType.IDENTIFICATION_COMPTE_UTILISATEUR_JETON_DEVEROUILLAGE_INCONNU);
		
		compteUtilisateur.setVerrou(null);
		
		switch(compteUtilisateur.getVerrou().getCause()){
		case ACCESS_MULTIPLE:
			infos.clear();
			mailService.send(new MailMessage("Compte SIGDCP", "Votre compte à été dévérouillé.") , compteUtilisateur.getUtilisateur().getContact().getEmail());
			break;
		case REINITIALISATION_PASSWORD:
			compteUtilisateur.getCredentials().setPassword(credentials.getPassword());//on ecrase son ancien mot de passe avec le nouveau
			mailService.send(new MailMessage("Compte SIGDCP", "Votre mot de passe à été reinitialiser et dévérouillé.") , compteUtilisateur.getUtilisateur().getContact().getEmail());
			break;
		}
		
		dao.update(compteUtilisateur);
	}
	
	/*
	private Boolean isValidTokenDeverouillage(String token){
		return token!=null;
	}*/
	
	@Override
	public CompteUtilisateur findByCodeVerrou(String codeVerrou) {
		return ((CompteUtilisateurDao)dao).readByTokenDeverouillage(codeVerrou);
	}

	
	@Override
	public Collection<ReponseSecrete> recupererPasswordEtape1(AgentEtat agentEtat) throws ServiceException {
		// Est ce que la saisie est cohérente
		//System.out.println("CompteUtilisateurServiceImpl.questionSecretes()");
		//serviceException(ServiceExceptionType.SAISIE_INCOHERENTE);
		
		AgentEtat agentEtatExistant = agentEtatDao.readByMatricule(agentEtat.getMatricule());
				
		if(agentEtatExistant==null || !agentEtatExistant.getMatricule().equalsIgnoreCase(agentEtat.getMatricule()) 
				|| !agentEtatExistant.getContact().getEmail().equals(agentEtat.getContact().getEmail())
				|| !agentEtatExistant.getType().equals(agentEtat.getType()))
			serviceException(ServiceExceptionType.SAISIE_INCOHERENTE);
		
		// est ce qu'il est associé a un compte utilisateur
		CompteUtilisateur compteUtilisateur = ((CompteUtilisateurDao)dao).readByMatricule(agentEtat.getMatricule());
		//System.out.println(compteUtilisateur);
		if(compteUtilisateur==null)
			serviceException(ServiceExceptionType.IDENTIFICATION_COMPTE_UTILISATEUR_INEXISTANT);

		Collection<ReponseSecrete> reponseSecretes = new LinkedList<>();
		for(ReponseSecrete reponseSecrete : compteUtilisateur.getReponseSecretes())
			reponseSecretes.add(new ReponseSecrete(reponseSecrete.getQuestion()));
		
		return reponseSecretes;		
	}
	

	@Override
	public void recupererPasswordEtape2(AgentEtat agentEtat,Collection<ReponseSecrete> reponseSecretes) throws ServiceException {
		CompteUtilisateur compteUtilisateur = ((CompteUtilisateurDao)dao).readByMatricule(agentEtat.getMatricule());
		if(compteUtilisateur==null)
			serviceException(ServiceExceptionType.IDENTIFICATION_COMPTE_UTILISATEUR_INEXISTANT);
		
		for(ReponseSecrete reponseSecreteCu : compteUtilisateur.getReponseSecretes())
			for(ReponseSecrete reponseSecreteSaisie : reponseSecretes)
				if(reponseSecreteCu.getQuestion().equals(reponseSecreteSaisie.getQuestion()))
					if(!reponseSecreteCu.getLibelle().equalsIgnoreCase(reponseSecreteSaisie.getLibelle()))
						serviceException(ServiceExceptionType.IDENTIFICATION_COMPTE_UTILISATEUR_REPONSE_INCORRECT);
		
		verouiller(compteUtilisateur,Cause.REINITIALISATION_PASSWORD);
	}
	
}
