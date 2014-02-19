package ci.gouv.budget.solde.sigdcp.service.calendrier;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import javax.ejb.Stateless;
import javax.inject.Inject;

import ci.gouv.budget.solde.sigdcp.dao.DynamicEnumerationDao;
import ci.gouv.budget.solde.sigdcp.dao.calendrier.MissionExecuteeDao;
import ci.gouv.budget.solde.sigdcp.dao.dossier.DeplacementDao;
import ci.gouv.budget.solde.sigdcp.dao.dossier.OperationDao;
import ci.gouv.budget.solde.sigdcp.model.Code;
import ci.gouv.budget.solde.sigdcp.model.calendrier.MissionExecutee;
import ci.gouv.budget.solde.sigdcp.model.dossier.Dossier;
import ci.gouv.budget.solde.sigdcp.model.dossier.DossierMission;
import ci.gouv.budget.solde.sigdcp.model.dossier.NatureOperation;
import ci.gouv.budget.solde.sigdcp.model.dossier.Operation;
import ci.gouv.budget.solde.sigdcp.model.dossier.PieceJustificative;
import ci.gouv.budget.solde.sigdcp.model.identification.Personne;
import ci.gouv.budget.solde.sigdcp.service.ActionType;
import ci.gouv.budget.solde.sigdcp.service.DefaultServiceImpl;
import ci.gouv.budget.solde.sigdcp.service.dossier.DeplacementService;
import ci.gouv.budget.solde.sigdcp.service.dossier.DossierService;
import ci.gouv.budget.solde.sigdcp.service.dossier.TraitementService;
import ci.gouv.budget.solde.sigdcp.service.utils.validaton.MissionExecuteeValidator;

@Stateless
public class MissionExecuteeServiceImpl extends DefaultServiceImpl<MissionExecutee, Long> implements MissionExecuteeService , Serializable {

	private static final long serialVersionUID = -7601857525393731774L;
	
	@Inject private DeplacementService deplacementService;
	@Inject private DossierService dossierService;
	@Inject private DeplacementDao deplacementDao;
	@Inject private OperationDao operationDao;
	@Inject private DynamicEnumerationDao dynamicEnumerationDao;
	@Inject private MissionExecuteeValidator validator;
	@Inject private TraitementService traitementService;
	//@Inject private DaoCreateHelper daoCreateHelper;
	
	@Inject 
	public MissionExecuteeServiceImpl(MissionExecuteeDao dao) {
		super(dao); 
	}

	@Override
	public void enregistrer(ActionType actionType,MissionExecutee missionExecutee, Collection<DossierMission> dossiers,Collection<Collection<PieceJustificative>> pieceJustificatives,Personne personne) {
		
		/*
		validator.validate(missionExecutee);
		if(!validator.isSucces())
			serviceException(validator.getMessagesAsString());
		*/
		switch(actionType){
		case ENREGISTRER:
			enregistrer(missionExecutee, dossiers, pieceJustificatives,personne);
			break;
		case SOUMETTRE:
			soumettre(missionExecutee, dossiers, pieceJustificatives,personne);
			break;
		}
	}
	
	private void enregistrer(MissionExecutee missionExecutee, Collection<DossierMission> dossiers,Collection<Collection<PieceJustificative>> pieceJustificatives,Personne personne){
		validator.setDossiers(dossiers);
		validator.setPieceJustificatives(pieceJustificatives);
		validate(validator, missionExecutee);
		
		//daoCreateHelper.init();
		if(((MissionExecuteeDao)dao).exist(missionExecutee.getId())){
			Iterator<Collection<PieceJustificative>> iterator = pieceJustificatives.iterator();
			deplacementDao.update(missionExecutee.getDeplacement());
			for(Dossier dossier : dossiers)
				dossierService.enregistrer(dossier, iterator.next(), personne);	
			dao.update(missionExecutee);
		}else{
			deplacementService.create(missionExecutee.getDeplacement());
			Iterator<Collection<PieceJustificative>> iterator = pieceJustificatives.iterator();
			for(Dossier dossier : dossiers)
				dossierService.enregistrer(dossier, iterator.next(), personne);	
			dao.create(missionExecutee);
		}
		
	}
	
	private void soumettre(MissionExecutee missionExecutee, Collection<DossierMission> dossiers,Collection<Collection<PieceJustificative>> pieceJustificatives,Personne personne){
		enregistrer(missionExecutee, dossiers, pieceJustificatives,personne);
		
		Operation operation = new Operation(new Date(),dynamicEnumerationDao.readByClass(NatureOperation.class,Code.NATURE_OPERATION_SOUMISSION),personne);
		operationDao.create(operation);
		
		for(Dossier dossier : dossiers)
			traitementService.create(operation, dossier, personne, Code.STATUT_RECEVABLE);
	}
	
	@Override
	public MissionExecutee findSaisieByPersonne(Personne personne) {
		return ((MissionExecuteeDao)dao).readSaisieByPersonne(personne);
	}
	
	@Override
	public MissionExecutee findByDossier(DossierMission dossierMission) {
		return ((MissionExecuteeDao)dao).readByDossier(dossierMission);
	}
	
}
