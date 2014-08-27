package ci.gouv.budget.solde.sigdcp.service.sampledata;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.inject.Inject;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;

import ci.gouv.budget.solde.sigdcp.dao.GenericDao;
import ci.gouv.budget.solde.sigdcp.dao.identification.AgentEtatDao;
import ci.gouv.budget.solde.sigdcp.model.Code;
import ci.gouv.budget.solde.sigdcp.model.calendrier.MissionExecutee;
import ci.gouv.budget.solde.sigdcp.model.dossier.Courrier;
import ci.gouv.budget.solde.sigdcp.model.dossier.Deplacement;
import ci.gouv.budget.solde.sigdcp.model.dossier.Document;
import ci.gouv.budget.solde.sigdcp.model.dossier.Dossier;
import ci.gouv.budget.solde.sigdcp.model.dossier.DossierDD;
import ci.gouv.budget.solde.sigdcp.model.dossier.DossierMission;
import ci.gouv.budget.solde.sigdcp.model.dossier.NatureDeplacement;
import ci.gouv.budget.solde.sigdcp.model.dossier.PieceJustificative;
import ci.gouv.budget.solde.sigdcp.model.dossier.ValidationType;
import ci.gouv.budget.solde.sigdcp.model.geographie.Localite;
import ci.gouv.budget.solde.sigdcp.model.identification.AgentEtat;
import ci.gouv.budget.solde.sigdcp.model.identification.CompteUtilisateur;
import ci.gouv.budget.solde.sigdcp.model.identification.Fonction;
import ci.gouv.budget.solde.sigdcp.model.identification.Grade;
import ci.gouv.budget.solde.sigdcp.model.identification.Party;
import ci.gouv.budget.solde.sigdcp.model.identification.Section;
import ci.gouv.budget.solde.sigdcp.service.ActionType;
import ci.gouv.budget.solde.sigdcp.service.SampleOperationService;
import ci.gouv.budget.solde.sigdcp.service.calendrier.MissionExecuteeService;
import ci.gouv.budget.solde.sigdcp.service.dossier.DossierDDService;
import ci.gouv.budget.solde.sigdcp.service.dossier.DossierMissionService;
import ci.gouv.budget.solde.sigdcp.service.fichier.FichierService;
import ci.gouv.budget.solde.sigdcp.service.identification.AuthentificationInfos;

public class SampleOperationServiceImpl implements SampleOperationService {
	
	@Inject private DossierDDService dossierDDService;
	@Inject private AuthentificationInfos authentificationInfos;
	@Inject private AgentEtatDao agentEtatDao;
	@Inject private GenericDao genericDao;
	@Inject private FichierService fichierService;
	@Inject private MissionExecuteeService missionService;
	@Inject DossierMissionService dossierMissionService;
	
	private Party tempLoggedParty; 
	private Random random = new Random();
	
	private String[] deplacements = {Code.NATURE_DEPLACEMENT_AFFECTATION};
	
	@Override
	public void recevoir() {
		valider(deplacements, Code.NATURE_OPERATION_CONFORMITE);
	}

	@Override
	public void deposer() {
		Collection<NatureDeplacement> natureDeplacements = new ArrayList<>();
		natureDeplacements.add(genericDao.readByClass(NatureDeplacement.class,Code.NATURE_DEPLACEMENT_AFFECTATION));
		Collection<DossierDD> dossiers = dossierDDService.findATraiter(natureDeplacements,Code.NATURE_OPERATION_RECEVABILITE);
		for(DossierDD dossier : dossiers){
			dossier.getCourrier().setDate(date());
			dossier.getCourrier().setNumero(numero());
		}
		dossierDDService.deposer(dossiers);
	}

	@Override
	public void confirmer() {
		valider(deplacements, Code.NATURE_OPERATION_CONFORMITE);
	}
	
	private void valider(String[] deplacements,String operation){
		Collection<NatureDeplacement> natureDeplacements = new ArrayList<>();
		for(String d : deplacements)
			natureDeplacements.add(genericDao.readByClass(NatureDeplacement.class,d));
		Collection<DossierDD> dossiers = dossierDDService.findATraiter(natureDeplacements,operation);
		for(Dossier dossier : dossiers)
			dossier.getTraitable().getTraitement().setValidationType(ValidationType.ACCEPTER);
		dossierDDService.valider(dossiers);
	}
	
	public void enregistrerDossier(){
		creerDossierDD(Code.NATURE_DEPLACEMENT_AFFECTATION, "900804D",true);//,
		organiserMission("900804D", "900804D");
	}
	
	public void soumettreDossier(){
		
	}
	
	private void logIn(Party utilisateur){
		if(authentificationInfos.getCompteUtilisateur()==null)
			authentificationInfos.setCompteUtilisateur(new CompteUtilisateur());
		tempLoggedParty = authentificationInfos.getUtilisateur();
		authentificationInfos.getCompteUtilisateur().setUtilisateur(utilisateur);
	}
	
	private void logOut(){
		authentificationInfos.getCompteUtilisateur().setUtilisateur(tempLoggedParty);
	}
	
	public DossierDD creerDossierDD(String codeNatDep,String mat,boolean soumettre){
		
		AgentEtat agentEtat = agentEtatDao.readByMatricule(mat);
		NatureDeplacement natureDeplacement = genericDao.readByClass(NatureDeplacement.class, codeNatDep);
		logIn(agentEtat);
		DossierDD dossier = dossierDDService.findDemande(natureDeplacement, null, null);
		
		List<Grade> grades = new ArrayList<>(genericDao.readAllByClass(Grade.class));
		dossier.setGrade(grades.get(random.nextInt(grades.size()-1)));
		dossier.setServiceOrigine(genericDao.readByClass(Section.class, Code.SECTION_DIRECTION_DGBF));
		dossier.setService(genericDao.readByClass(Section.class, Code.SECTION_DIRECTION_DGBF));
		dossier.setDatePriseService(date());
		Deplacement deplacement = dossier.getDeplacement();
		deplacement.setLocaliteDepart(genericDao.readByClass(Localite.class, Code.LOCALITE_ABIDJAN));
		deplacement.setLocaliteArrivee(genericDao.readByClass(Localite.class, Code.LOCALITE_BOUAKE));
		deplacement.setDateDepart(date());
		deplacement.setDateArrivee(deplacement.getDateDepart());
		
		documents(dossier);
		dossierDDService.enregistrer(ActionType.ENREGISTRER,dossier);
		
		dossier = dossierDDService.findDemande(natureDeplacement, null, null);
		documents(dossier);
		dossierDDService.enregistrer(ActionType.SOUMETTRE,dossier);
		
		logOut();
		
		return null;//dossier;
	}
	
	public void organiserMission(String organisateurMat,String...participantMats){
		/*
		AgentEtat organisateur = agentEtatDao.readByMatricule(organisateurMat);
		logIn(organisateur);
		MissionExecuteeDto missionExecuteeDto = missionService.findSaisieByNumero(null);
		
		missionExecuteeDto.getMissionExecutee().setDesignation("Une mission");
		missionExecuteeDto.getMissionExecutee().getDeplacement().setLocaliteArrivee(genericDao.readByClass(Localite.class, Code.LOCALITE_PARIS));
		missionExecuteeDto.getMissionExecutee().getDeplacement().setDateDepart(date(1, 4, 2014));
		missionExecuteeDto.getMissionExecutee().getDeplacement().setDateArrivee(date(3, 4, 2014));
		document(missionExecuteeDto.getCommunication());
		for(String m : participantMats){
			DossierDto d = dossierMission(missionExecuteeDto, m);
			if(missionExecuteeDto.getMissionExecutee().getDossierDuResponsable()==null)
				missionExecuteeDto.getMissionExecutee().setDossierDuResponsable((DossierMission) d.getDossier());
			missionExecuteeDto.getDossierDtos().add(d);
		}
		missionService.enregistrer(ActionType.ENREGISTRER, missionExecuteeDto.getMissionExecutee(), missionExecuteeDto.getCommunication(), missionExecuteeDto.getDossierDtos());
		
		missionExecuteeDto = missionService.findSaisieByNumero(null);
		missionService.enregistrer(ActionType.SOUMETTRE, missionExecuteeDto.getMissionExecutee(), missionExecuteeDto.getCommunication(), missionExecuteeDto.getDossierDtos());
	
		logOut();
		*/
	}
	
	private DossierMission dossierMission(MissionExecutee missionExecutee,String matricule){
		/*
		DossierMission d = dossierMissionService.buildDto(new DossierMission(missionExecuteeDto.getMissionExecutee().getDeplacement()), Code.NATURE_OPERATION_SAISIE);
		d.setBeneficiaire(agentEtatDao.readByMatricule(matricule));
		d.setFonction(genericDao.readByClass(Fonction.class,"FONCTION1"));
		d.getFrais().setDivers(new BigDecimal(1000000));
		d.getFrais().setIndemnite(new BigDecimal(1000000));
		d.getFrais().setTransport(new BigDecimal(1000000));
		documents(d);
		*/
		return null;// d;
	}
	
	static Date date(){
		return new Date();
	}
	
	static Date date(int j,int m,int a){
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_MONTH, j);
		calendar.set(Calendar.MONTH, m);
		calendar.set(Calendar.YEAR, a);
		return calendar.getTime();
	}
	
	static String numero(){
		return RandomStringUtils.randomAlphanumeric(6);
	}
	
	static Courrier courrier(){
		return new Courrier(numero(),new Date());
	}

	private void document(Document document){
		if(StringUtils.isEmpty(document.getNumero()))
			document.setNumero(numero());
		if(document.getDateEtablissement()==null)
			document.setDateEtablissement(date());
		if(document.getFonctionSignataire()==null)
			document.setFonctionSignataire(genericDao.readByClass(Fonction.class, "FONCTION1"));
		if(document.getFichier()==null){
			try {
				ByteArrayOutputStream bytes = new ByteArrayOutputStream();
				InputStream is = this.getClass().getResourceAsStream("p1.jpg");
				IOUtils.copy(is, bytes);
				IOUtils.closeQuietly(is);
				document.setFichier(fichierService.convertir(bytes.toByteArray(), "p1.jpg"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void documents(Dossier dossierDto){
		for(PieceJustificative p : dossierDto.getPieceJustificatives()){
			document(p);
		}
	}

}
