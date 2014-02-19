package ci.gouv.budget.solde.sigdcp.dao.calendrier;

import java.io.Serializable;

import javax.persistence.NoResultException;

import ci.gouv.budget.solde.sigdcp.dao.JpaDaoImpl;
import ci.gouv.budget.solde.sigdcp.model.Code;
import ci.gouv.budget.solde.sigdcp.model.calendrier.MissionExecutee;
import ci.gouv.budget.solde.sigdcp.model.dossier.DossierMission;
import ci.gouv.budget.solde.sigdcp.model.identification.Personne;

public class MissionExecuteeDaoImpl extends JpaDaoImpl<MissionExecutee, Long> implements MissionExecuteeDao, Serializable {

	private static final long serialVersionUID = -2609724288199083806L;

	@Override
	public MissionExecutee readSaisieByPersonne(Personne personne) {
		try{
			return entityManager.createQuery("SELECT mission FROM MissionExecutee mission WHERE EXISTS "
					+ "( SELECT dm FROM DossierMission dm WHERE dm.dernierTraitement.statut.code = :statutId "
					+ " AND dm.dernierTraitement.motif IS NULL AND dm.deplacement = mission.deplacement AND dm.dernierTraitement.operation.creePar = :personne)"
					, clazz)
					.setParameter("personne", personne)
					.setParameter("statutId", Code.STATUT_SAISIE)
					.getSingleResult();
		}catch(NoResultException e){
			return null;
		}
		
	}
	
	@Override
	public MissionExecutee readByDossier(DossierMission dossierMission) {
		try{
			return entityManager.createQuery("SELECT mission FROM MissionExecutee mission WHERE mission.deplacement= :deplacement "
					, clazz)
					.setParameter("deplacement", dossierMission.getDeplacement())
					.getSingleResult();
		}catch(NoResultException e){
			return null;
		}
		
	}

}