package ci.gouv.budget.solde.sigdcp.service.calendrier;

import java.io.Serializable;
import java.util.Collection;

import javax.ejb.Stateless;
import javax.inject.Inject;

import ci.gouv.budget.solde.sigdcp.dao.calendrier.MissionExecuteeDao;
import ci.gouv.budget.solde.sigdcp.dao.dossier.DeplacementDao;
import ci.gouv.budget.solde.sigdcp.dao.dossier.DossierDao;
import ci.gouv.budget.solde.sigdcp.model.calendrier.MissionExecutee;
import ci.gouv.budget.solde.sigdcp.model.dossier.Dossier;
import ci.gouv.budget.solde.sigdcp.model.dossier.DossierMission;
import ci.gouv.budget.solde.sigdcp.model.dossier.PieceJustificative;
import ci.gouv.budget.solde.sigdcp.model.identification.Personne;
import ci.gouv.budget.solde.sigdcp.service.ActionType;
import ci.gouv.budget.solde.sigdcp.service.DefaultServiceImpl;
import ci.gouv.budget.solde.sigdcp.service.utils.validaton.MissionExecuteeValidator;

@Stateless
public class MissionExecuteeServiceImpl extends DefaultServiceImpl<MissionExecutee, Long> implements MissionExecuteeService , Serializable {

	private static final long serialVersionUID = -7601857525393731774L;
	
	@Inject private DeplacementDao deplacementDao;
	@Inject private DossierDao dossierDao;
	@Inject private MissionExecuteeValidator validator;
	
	@Inject 
	public MissionExecuteeServiceImpl(MissionExecuteeDao dao) {
		super(dao);
	}

	@Override
	public void enregistrer(ActionType actionType,MissionExecutee missionExecutee, Collection<DossierMission> dossiers,Collection<Collection<PieceJustificative>> pieceJustificatives) {
		validator.setDossiers(dossiers);
		validator.setPieceJustificatives(pieceJustificatives);
		validator.validate(missionExecutee);
		if(!validator.isSucces())
			serviceException(validator.getMessagesAsString());
		
		deplacementDao.create(missionExecutee.getDeplacement());
		for(Dossier dossier : dossiers)
			dossierDao.create(dossier);
		
		dao.create(missionExecutee);
	}
	
	@Override
	public MissionExecutee findSaisieByPersonne(Personne personne) {
		return ((MissionExecuteeDao)dao).readSaisieByPersonne(personne);
	}
	

	
}
