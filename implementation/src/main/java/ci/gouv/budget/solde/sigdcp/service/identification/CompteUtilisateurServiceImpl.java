package ci.gouv.budget.solde.sigdcp.service.identification;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;

import javax.ejb.Stateless;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.ServletRequest;

import org.apache.commons.lang3.RandomStringUtils;

import ci.gouv.budget.solde.sigdcp.dao.identification.AgentEtatDao;
import ci.gouv.budget.solde.sigdcp.dao.identification.CompteUtilisateurDao;
import ci.gouv.budget.solde.sigdcp.dao.identification.DelegueSotraDao;
import ci.gouv.budget.solde.sigdcp.model.Code;
import ci.gouv.budget.solde.sigdcp.model.communication.NotificationMessageType;
import ci.gouv.budget.solde.sigdcp.model.identification.AgentEtat;
import ci.gouv.budget.solde.sigdcp.model.identification.CompteUtilisateur;
import ci.gouv.budget.solde.sigdcp.model.identification.Credentials;
import ci.gouv.budget.solde.sigdcp.model.identification.DelegueSotra;
import ci.gouv.budget.solde.sigdcp.model.identification.Party;
import ci.gouv.budget.solde.sigdcp.model.identification.ReponseSecrete;
import ci.gouv.budget.solde.sigdcp.model.identification.Role;
import ci.gouv.budget.solde.sigdcp.model.identification.Verrou;
import ci.gouv.budget.solde.sigdcp.model.identification.Verrou.Cause;
import ci.gouv.budget.solde.sigdcp.service.DefaultServiceImpl;
import ci.gouv.budget.solde.sigdcp.service.ServiceException;
import ci.gouv.budget.solde.sigdcp.service.ServiceExceptionType;

@Stateless
public class CompteUtilisateurServiceImpl extends DefaultServiceImpl<CompteUtilisateur, Long> implements CompteUtilisateurService {

	private static final long serialVersionUID = 159214876975685747L;
	
	private static final Integer MAX_TENTATIVE_AUTH = 3;
	
	@Inject private AuthentificationInfos infos;
	@Inject private AgentEtatDao agentEtatDao;
	@Inject private DelegueSotraDao delegueSotraDao;
	
	@Inject
	public CompteUtilisateurServiceImpl(CompteUtilisateurDao dao) {
		super(dao);
	}

	@Override
	public CompteUtilisateur authentifier(Credentials credentials) throws ServiceException {
		//if(infos.getTimestampDebut()!=null)
		//	serviceException(ServiceExceptionType.IDENTIFICATION_COMPTE_UTILISATEUR_CONNECTE);
		
		CompteUtilisateur compteUtilisateur = ((CompteUtilisateurDao)dao).readByUsername(credentials.getUsername());// readByCredentials(credentials);
		//Boolean verouille = compteUtilisateur!=null && compteUtilisateur.getVerrou()!=null /*&& isValidTokenDeverouillage(compteUtilisateur.getTokenDeverouillage())*/;
		
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
			if(compteUtilisateur.getVerrou()==null && infos.getNombreTentative() == MAX_TENTATIVE_AUTH){
				verouiller(compteUtilisateur,Cause.ACCESS_MULTIPLE);
				serviceException(ServiceExceptionType.IDENTIFICATION_COMPTE_UTILISATEUR_INCONNU,Boolean.FALSE);//we do not roll back transaction
			}else
				serviceException(ServiceExceptionType.IDENTIFICATION_COMPTE_UTILISATEUR_INCONNU);
		}
		
		if(compteUtilisateur.getVerrou()!=null){
			notifierVerrou(compteUtilisateur);
			serviceException(ServiceExceptionType.IDENTIFICATION_COMPTE_UTILISATEUR_VEROUILLE);
		}
		
		infos.setTimestampDebut(System.currentTimeMillis());
		authentificationInfos.setCompteUtilisateur(compteUtilisateur);
		return compteUtilisateur;
	}
	
	@Override
	public void deconnecter(CompteUtilisateur compteUtilisateur) throws ServiceException {
		if(compteUtilisateur==null || infos.getTimestampDebut()==null)
			return;
		//notifier(NotificationMessageType.AVIS_COMPTE_UTILISATEUR_ETAT_SESSION,new Object[]{"nomPrenomsAgentEtat",compteUtilisateur.getUtilisateur().getNom(),
		//		"dateHeureConnexion",formatDate(new Date(infos.getTimestampDebut())),"dateHeureDeconnexion",formatDate(new Date()),"adresseIP","000.000.000.000","adresseGeographique","A déterminer"}, compteUtilisateur);
		infos.clear();
	}
	
	@Override
	public void verouiller(CompteUtilisateur compteUtilisateur,Cause causeVerrouillage) throws ServiceException {
		compteUtilisateur.setVerrou(new Verrou(RandomStringUtils.randomAlphanumeric(64), causeVerrouillage, new Date(), RandomStringUtils.randomAlphanumeric(32)));
		dao.update(compteUtilisateur);
		notifierVerrou(compteUtilisateur);
	}
	
	private void notifierVerrou(CompteUtilisateur compteUtilisateur){
		switch(compteUtilisateur.getVerrou().getCause()){
		case ACCESS_MULTIPLE:
			notifier(NotificationMessageType.AVIS_COMPTE_UTILISATEUR_VERROUILLE_ACCES_MULTIPLE,
					new Object[]{"nomPrenomsAgentEtat",compteUtilisateur.getUtilisateur().getNom(),"codeDeverouillage",compteUtilisateur.getVerrou().getJeton(),
					"lienDeverouillage",lienDeverouillage(compteUtilisateur),"dateHeureVerouillage",formatDate(new Date()),"adresseIP","000.000.000.000","adresseGeographique","A déterminer"},compteUtilisateur);
			break;
			
		case REINITIALISATION_PASSWORD:
			notifier(NotificationMessageType.AVIS_COMPTE_UTILISATEUR_VERROUILLE_REINITIALISATION_PASSWORD,
					new Object[]{"nomPrenomsAgentEtat",compteUtilisateur.getUtilisateur().getNom(),"codeDeverouillage",compteUtilisateur.getVerrou().getJeton(),
					"lienDeverouillage",lienDeverouillage(compteUtilisateur)},compteUtilisateur);
			break;
			
		case DESACTIVATION_COMPTE:
			notifier(NotificationMessageType.AVIS_COMPTE_UTILISATEUR_VERROUILLE_REINITIALISATION_PASSWORD,
					new Object[]{"nomPrenomsAgentEtat",compteUtilisateur.getUtilisateur().getNom()},compteUtilisateur);
			break;
		}
	}
	
	private String lienDeverouillage(CompteUtilisateur compteUtilisateur){
		ServletRequest request = (ServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		switch(compteUtilisateur.getVerrou().getCause()){
		case ACCESS_MULTIPLE:
			return navigationHelper.addQueryParameters(request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getServletContext().getContextPath()+"/public/deverouillage.jsf",
					new Object[]{constantResources.getWebRequestParamVerrouCode(),compteUtilisateur.getVerrou().getCode(),constantResources.getWebRequestParamVerrouCause(),
				constantResources.getWebRequestParamVerrouCause(compteUtilisateur.getVerrou().getCause())});
		case REINITIALISATION_PASSWORD:
			return navigationHelper.addQueryParameters(request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getServletContext().getContextPath()+"/public/reinitialiserpassword.jsf",
					new Object[]{constantResources.getWebRequestParamVerrouCode(),compteUtilisateur.getVerrou().getCode(),constantResources.getWebRequestParamVerrouCause(),
				constantResources.getWebRequestParamVerrouCause(compteUtilisateur.getVerrou().getCause())});
		}
		return null;
	}
	
	@Override
	public void deverouiller(Verrou verrou,Credentials credentials) throws ServiceException {
		CompteUtilisateur compteUtilisateur = ((CompteUtilisateurDao)dao).readByUsername(credentials.getUsername());
		if(compteUtilisateur==null)
			serviceException(ServiceExceptionType.IDENTIFICATION_COMPTE_UTILISATEUR_INEXISTANT);
		
		//est ce que le compte est verouille
		if(compteUtilisateur.getVerrou()==null)
			serviceException(ServiceExceptionType.IDENTIFICATION_COMPTE_UTILISATEUR_ACTIF);
				
		if(compteUtilisateur.getVerrou().getCause()==null || !compteUtilisateur.getVerrou().getCode().equals(verrou.getCode()))
			serviceException(ServiceExceptionType.IDENTIFICATION_COMPTE_UTILISATEUR_CODE_DEVEROUILLAGE_INCONNU);
		
		if(Cause.ACCESS_MULTIPLE.equals(verrou.getCause()) && !compteUtilisateur.getCredentials().getPassword().equals(credentials.getPassword()) )
			serviceException(ServiceExceptionType.IDENTIFICATION_COMPTE_UTILISATEUR_INEXISTANT);
			
		if(!compteUtilisateur.getVerrou().getJeton().equals(verrou.getJeton()))
			serviceException(ServiceExceptionType.IDENTIFICATION_COMPTE_UTILISATEUR_JETON_DEVEROUILLAGE_INCONNU);
				
		switch(verrou.getCause()){
		case ACCESS_MULTIPLE:
			infos.clear();
			notifier(NotificationMessageType.AVIS_COMPTE_UTILISATEUR_DEVERROUILLE_ACCES_MULTIPLE,new Object[]{"nomPrenomsAgentEtat",compteUtilisateur.getUtilisateur().getNom()},
					compteUtilisateur);
			break;
		case REINITIALISATION_PASSWORD:
			compteUtilisateur.getCredentials().setPassword(credentials.getPassword());//on ecrase son ancien mot de passe avec le nouveau
			notifier(NotificationMessageType.AVIS_COMPTE_UTILISATEUR_DEVERROUILLE_REINITIALISATION_PASSWORD,new Object[]{"nomPrenomsAgentEtat",compteUtilisateur.getUtilisateur().getNom(),"loginUtilisateur",credentials.getUsername(),
					"motPasseUtilisateur",credentials.getPassword()},
					compteUtilisateur);
			break;
		}
		compteUtilisateur.setVerrou(null);
		dao.update(compteUtilisateur);
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
	
	@Override 
	public void deverouillable(Verrou verrou) throws ServiceException {
		CompteUtilisateur compteUtilisateur = ((CompteUtilisateurDao)dao).readByCodeVerrouByCauseVerrou(verrou.getCode(), verrou.getCause());
		if(compteUtilisateur==null)
			serviceException(ServiceExceptionType.IDENTIFICATION_COMPTE_UTILISATEUR_CODE_DEVEROUILLAGE_INCONNU);
	}
	
	@Override
	public void modifierRoles(CompteUtilisateur compteUtilisateur,Collection<Role> nouveauxRoles,DelegueSotra delegueSotra) throws ServiceException {
		//suppression de roles
		Collection<Role> suppression = new HashSet<>();
		for(Role role : compteUtilisateur.getRoles())
			if(!nouveauxRoles.contains(role))
				suppression.add(role);
		for(Role role : suppression){
			compteUtilisateur.getRoles().remove(role);
			switch(role.getCode()){
			
			default:break;
			}
		}
		
		//ajout de roles
		Collection<Role> ajout = new HashSet<>();
		for(Role role : nouveauxRoles)
			if(!compteUtilisateur.getRoles().contains(role))
				ajout.add(role);
		
		for(Role role : ajout){
			compteUtilisateur.getRoles().add(role);
			switch(role.getCode()){
			case Code.ROLE_DELEGUE_SOTRA:
				if(compteUtilisateur.getUtilisateur() instanceof AgentEtat){
					if(delegueSotraDao.readByAgentEtat((AgentEtat) compteUtilisateur.getUtilisateur())==null)
						delegueSotraDao.create(delegueSotra);
					delegueSotraDao.update(delegueSotra);
				}
				break;
			default:break;
			}
		}
		
		if(!suppression.isEmpty() || !ajout.isEmpty())
			((CompteUtilisateurDao)dao).update(compteUtilisateur);
	}
	
	@Override
	public CompteUtilisateur findByCredentials(Credentials credentials) {
		return ((CompteUtilisateurDao)dao).readByCredentials(credentials);
	}

	@Override
	public Collection<CompteUtilisateur> findByTypeByRole(Class<? extends Party> aClass,Role role) {
		return ((CompteUtilisateurDao)dao).readByTypeByRole(aClass, role);
	}
	
	@Override
	public CompteUtilisateur findByParty(Party party) {
		return ((CompteUtilisateurDao)dao).readByParty(party);
	}
	
}
