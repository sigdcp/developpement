package ci.gouv.budget.solde.sigdcp.service.calendrier;

import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedList;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import ci.gouv.budget.solde.sigdcp.dao.calendrier.MissionExecuteeDao;
import ci.gouv.budget.solde.sigdcp.dao.dossier.DeplacementDao;
import ci.gouv.budget.solde.sigdcp.dao.dossier.DossierDao;
import ci.gouv.budget.solde.sigdcp.dao.dossier.PieceJustificativeAFournirDao;
import ci.gouv.budget.solde.sigdcp.dao.dossier.PieceJustificativeDao;
import ci.gouv.budget.solde.sigdcp.model.Code;
import ci.gouv.budget.solde.sigdcp.model.calendrier.MissionExecutee;
import ci.gouv.budget.solde.sigdcp.model.dossier.Dossier;
import ci.gouv.budget.solde.sigdcp.model.dossier.DossierMission;
import ci.gouv.budget.solde.sigdcp.model.dossier.NatureDeplacement;
import ci.gouv.budget.solde.sigdcp.model.dossier.PieceJustificative;
import ci.gouv.budget.solde.sigdcp.model.dossier.TypeDepense;
import ci.gouv.budget.solde.sigdcp.model.dossier.ValidationType;
import ci.gouv.budget.solde.sigdcp.model.fichier.Fichier;
import ci.gouv.budget.solde.sigdcp.model.geographie.Localite;
import ci.gouv.budget.solde.sigdcp.model.identification.AgentEtat;
import ci.gouv.budget.solde.sigdcp.model.indemnite.GroupeMission;
import ci.gouv.budget.solde.sigdcp.model.traitement.NatureOperation;
import ci.gouv.budget.solde.sigdcp.model.traitement.Operation;
import ci.gouv.budget.solde.sigdcp.model.traitement.TraitementDossier;
import ci.gouv.budget.solde.sigdcp.service.ActionType;
import ci.gouv.budget.solde.sigdcp.service.DefaultServiceImpl;
import ci.gouv.budget.solde.sigdcp.service.ServiceExceptionType;
import ci.gouv.budget.solde.sigdcp.service.dossier.DeplacementService;
import ci.gouv.budget.solde.sigdcp.service.dossier.DossierMissionService;
import ci.gouv.budget.solde.sigdcp.service.dossier.OperationService;
import ci.gouv.budget.solde.sigdcp.service.indemnite.GroupeMissionService;
import ci.gouv.budget.solde.sigdcp.service.traitement.TraitementDossierService;
import ci.gouv.budget.solde.sigdcp.service.utils.validaton.MissionExecuteeValidator;

@Stateless
public class MissionExecuteeServiceImpl extends DefaultServiceImpl<MissionExecutee, Long> implements MissionExecuteeService , Serializable {

	private static final long serialVersionUID = -7601857525393731774L;
	
	@Inject private DeplacementService deplacementService;
	@Inject private DossierMissionService dossierService;
	@Inject private DossierDao dossierDao;
	@Inject private DeplacementDao deplacementDao;
	@Inject private OperationService operationService;
	@Inject private MissionExecuteeValidator validator;
	@Inject private TraitementDossierService traitementService;
	@Inject private PieceJustificativeDao pieceJustificativeDao;
	@Inject private PieceJustificativeAFournirDao pieceJustificativeAFournirDao;
	@Inject private GroupeMissionService groupeMissionService;
	
	@Inject 
	public MissionExecuteeServiceImpl(MissionExecuteeDao dao) {
		super(dao); 
	}

	@Override
	public void enregistrer(ActionType actionType,MissionExecutee missionExecutee) {
		/*
		validator.validate(missionExecutee);
		if(!validator.isSucces())
			serviceException(validator.getMessagesAsString());
		*/
		
		switch(actionType){
		case ENREGISTRER:
			enregistrer(missionExecutee);
			break;
		case SOUMETTRE:
			soumettre(missionExecutee);
			break;
		}
	}
	
	private void enregistrer(MissionExecutee missionExecutee){
		//validator.setDossiers(dossiers);
		//validator.setPieceJustificatives(pieceJustificatives);
		validate(validator, missionExecutee);
		
		//daoCreateHelper.init();
		if(((MissionExecuteeDao)dao).exist(missionExecutee.getId())){
			//Iterator<Collection<PieceJustificative>> iterator = pieceJustificatives.iterator();
			deplacementDao.update(missionExecutee.getDeplacement());
			for(DossierMission dossier : missionExecutee.getDossiers()){
				if(dossierDao.exist(dossier.getId())){
					for(PieceJustificative pj : dossier.getPieceJustificatives())
						if(pj.getNumero().equals(missionExecutee.getCommunication().getNumero())){
							pj.setNumero(missionExecutee.getCommunication().getNumero());
							pj.setDateEtablissement(missionExecutee.getCommunication().getDateEtablissement());
							pj.setFonctionSignataire(missionExecutee.getCommunication().getFonctionSignataire());
							if(missionExecutee.getCommunication().getFichier().getId()==null)
								pj.setFichier(missionExecutee.getCommunication().getFichier());
							break;
						}
					}
				else 
					creerDossier(dossier, missionExecutee.getCommunication());
				dossierService.enregistrer(ActionType.ENREGISTRER,dossier);
			}
			dao.update(missionExecutee);
		}else{
			
			deplacementService.creer(missionExecutee.getDeplacement());
			for(DossierMission dossier : missionExecutee.getDossiers()){
				creerDossier(dossier, missionExecutee.getCommunication());
				dossierService.enregistrer(ActionType.ENREGISTRER,dossier);	
			}
			
			dao.create(missionExecutee);
		}	
	}
	
	private void creerDossier(DossierMission dossier,PieceJustificative communication){
		PieceJustificative p = new PieceJustificative();
		p.setDateEtablissement(communication.getDateEtablissement());
		p.setDossier(dossier);
		p.setFichier(new Fichier(communication.getFichier()));
		p.setFonctionSignataire(communication.getFonctionSignataire());
		p.setModel(communication.getModel());
		p.setNumero(communication.getNumero());
		dossier.getPieceJustificatives().add(p);
	}
	
	private void soumettre(MissionExecutee missionExecutee){
		enregistrer(missionExecutee);
		Operation operation = operationService.creer(Code.NATURE_OPERATION_SOUMISSION);
		for(DossierMission dossier : missionExecutee.getDossiers()){
	//AKM
			GroupeMission groupe = groupeMissionService.findByFonctionOrProfession(dossier.getFonction(), dossier.getProfession()) ;
			//dossier.setGrade(dossier.getProfession().getGrade());
			dossier.setGroupe(groupe);
			dossier.setClasseVoyage(groupe.getClasseVoyage());
	//*********************************************************************************************************************
			TraitementDossier td = new TraitementDossier();
			td.setValidationType(ValidationType.ACCEPTER);
			traitementService.creer(operation, dossier,td, Code.STATUT_ACCEPTE);
			//pour le traitement suivant
			dossier.getTraitable().setTraitement(new TraitementDossier());
			dossier.getTraitable().setNatureOperation(genericDao.readByClass(NatureOperation.class, Code.NATURE_OPERATION_RECEVABILITE));
			dossier.getTraitable().getTraitement().setValidationType(ValidationType.ACCEPTER);
		}
		dossierService.valider(missionExecutee.getDossiers());
	}
	
	@Override
	public void transmettreDossier(Collection<DossierMission> dossiers) {
		Operation operation = operationService.creer(Code.NATURE_OPERATION_TRANSMISSION_SAISIE_A_BENEFICIAIRE);
		for(Dossier dossier : dossiers){
			TraitementDossier td = new TraitementDossier();
			td.setValidationType(ValidationType.ACCEPTER);
			traitementService.creer(operation, dossier,td, Code.STATUT_ACCEPTE);
		}
	}
	
	@TransactionAttribute(TransactionAttributeType.NEVER)
	@Override
	public MissionExecutee findSaisieByNumero(Long id) {
		MissionExecutee missionExecutee = null;
		if(id!=null){//consultation de mission
			missionExecutee = ((MissionExecuteeDao)dao).read(id);
			if(missionExecutee==null)
				serviceException(ServiceExceptionType.RESOURCE_NOT_FOUND);
			init(missionExecutee,null);
			/*for(Dossier dossier : dossierService.findByDeplacement(missionExecutee.getDeplacement())){
				DossierDto dossierDto = new 
				dto.getDossierDtos().add(DossierServiceImpl.dto(dossier, personne));
				if(dossier.getCourrier()==null || StringUtils.isEmpty(dossier.getCourrier().getNumero()))
					dto.set
			}
			return dto;*/
		}else{
			missionExecutee = ((MissionExecuteeDao)dao).readSaisieByPersonne(utilisateur());
			if(missionExecutee==null){//nouvelle mission a saisir
				
				missionExecutee = new MissionExecutee();
				missionExecutee.setOrganisateur((AgentEtat) utilisateur());
				init(missionExecutee,Code.NATURE_OPERATION_ORGANISATION_DEPLACEMENT);
				missionExecutee.getDeplacement().setNature(genericDao.readByClass(NatureDeplacement.class, Code.NATURE_DEPLACEMENT_MISSION_HCI));
				missionExecutee.getDeplacement().setTypeDepense(genericDao.readByClass(TypeDepense.class, Code.TYPE_DEPENSE_PRISE_EN_CHARGE));
				missionExecutee.getDeplacement().setLocaliteDepart(genericDao.readByClass(Localite.class, Code.LOCALITE_ABIDJAN));
				
				//a supprimer - juste pour test
				/*
				missionExecutee.setDesignation("Ma mission");
				missionExecutee.getDeplacement().setDateDepart(new Date());
				missionExecutee.getDeplacement().setDateArrivee(new Date());
				missionExecutee.getCommunication().setNumero("a");
				missionExecutee.getCommunication().setDateEtablissement(new Date());
				missionExecutee.getCommunication().setFonctionSignataire(genericDao.readByClass(Fonction.class, "FONCTION1"));
				missionExecutee.getDeplacement().setLocaliteArrivee(genericDao.readByClass(Localite.class, Code.LOCALITE_PARIS));
				*/
			
			}else{//mission en cours de saisie
				
				init(missionExecutee,null);
				
			}
			
		}
		
		return missionExecutee;
	}
	
	@TransactionAttribute(TransactionAttributeType.NEVER)
	@Override
	public MissionExecutee findByDossier(DossierMission dossierMission) {
		return ((MissionExecuteeDao)dao).readByDossier(dossierMission);
	}
	
	@TransactionAttribute(TransactionAttributeType.NEVER)
	@Override
	public Collection<MissionExecutee> findMissionOrganisees() {
		Collection<MissionExecutee> liste = new LinkedList<>();
		for(MissionExecutee me : ((MissionExecuteeDao)dao).readByPersonne(utilisateur())){
			init(me,null);
			liste.add(me);
		}

		return liste;
	}
	
	@TransactionAttribute(TransactionAttributeType.NEVER)
	public void init(MissionExecutee missionExecutee,String natureOperation){	
		
		if(Code.NATURE_OPERATION_ORGANISATION_DEPLACEMENT.equals(natureOperation)){
			
		}
		
		missionExecutee.getDossiers().clear();
		for(DossierMission dossier : dossierService.findByDeplacement(missionExecutee.getDeplacement())){
			dossierService.init(dossier, natureOperation);
			
			missionExecutee.getDossiers().add(dossier);
			
			if(Code.NATURE_OPERATION_TRANSMISSION_SAISIE_A_BENEFICIAIRE.equals(dossier.getTraitable().getDernierTraitement().getOperation().getNature().getCode()))
				missionExecutee.setTousPresent(false);
		}
		
		missionExecutee.setNatureOperationCode((missionExecutee.getDossiers().isEmpty() || !missionExecutee.getTousPresent())?Code.NATURE_OPERATION_SAISIE:missionExecutee.getDossiers().iterator().next().getTraitable().getNatureOperation().getCode());
		
		Collection<PieceJustificative> communications = pieceJustificativeDao.readByDeplacementByTypeId(missionExecutee.getDeplacement(), Code.TYPE_PIECE_COMMUNICATION);
		
		missionExecutee.setCommunication(communications.isEmpty()?new PieceJustificative(null,pieceJustificativeAFournirDao.readAdministrativeByNatureDeplacementIdByTypeDepenseId(Code.NATURE_DEPLACEMENT_MISSION_HCI, Code.TYPE_DEPENSE_PRISE_EN_CHARGE) ):communications.iterator().next());
		missionExecutee.setTypeDepense(missionExecutee.getDossiers().isEmpty()?genericDao.readByClass(TypeDepense.class, Code.TYPE_DEPENSE_PRISE_EN_CHARGE):missionExecutee.getDossiers().iterator().next().getDeplacement().getTypeDepense());
	}

	@TransactionAttribute(TransactionAttributeType.NEVER)
	@Override
	public Collection<MissionExecutee> findACoter() {
		Collection<MissionExecutee> liste = new LinkedList<>();
		for(MissionExecutee me : ((MissionExecuteeDao)dao).readACoter()){
			init(me,null);
			liste.add(me);
	}
		return liste;
	}
}
