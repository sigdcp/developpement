package ci.gouv.budget.solde.sigdcp.service.calendrier;

import java.io.Serializable;
import java.util.Collection;

import javax.ejb.Stateless;
import javax.inject.Inject;

import ci.gouv.budget.solde.sigdcp.dao.calendrier.MissionDao;
import ci.gouv.budget.solde.sigdcp.model.calendrier.Mission;
import ci.gouv.budget.solde.sigdcp.model.dossier.DossierMission;
import ci.gouv.budget.solde.sigdcp.model.dossier.PieceJustificative;
import ci.gouv.budget.solde.sigdcp.model.identification.Personne;
import ci.gouv.budget.solde.sigdcp.service.DefaultServiceImpl;

@Stateless
public class MissionServiceImpl extends DefaultServiceImpl<Mission, Long> implements MissionService , Serializable {

	private static final long serialVersionUID = -7601857525393731774L;
	
	@Inject 
	public MissionServiceImpl(MissionDao dao) {
		super(dao);
	}

	@Override
	public void enregistrer(Mission mission, Collection<DossierMission> dossiers,Collection<Collection<PieceJustificative>> pieceJustificatives) {
		System.out.println("MissionServiceImpl.enregistrer()");
	}
	
	@Override
	public void soumettre(Mission mission, Collection<DossierMission> dossiers,Collection<Collection<PieceJustificative>> pieceJustificatives) {
		System.out.println("MissionServiceImpl.soumettre()");
	}
	
	@Override
	public Mission findSaisieByPersonne(Personne personne) {
		return ((MissionDao)dao).readSaisieByPersonne(personne);
	}
	

	
}
