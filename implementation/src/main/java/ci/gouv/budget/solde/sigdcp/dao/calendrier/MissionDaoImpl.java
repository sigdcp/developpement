package ci.gouv.budget.solde.sigdcp.dao.calendrier;

import java.io.Serializable;

import javax.persistence.NoResultException;

import ci.gouv.budget.solde.sigdcp.dao.JpaDaoImpl;
import ci.gouv.budget.solde.sigdcp.model.Code;
import ci.gouv.budget.solde.sigdcp.model.calendrier.Mission;
import ci.gouv.budget.solde.sigdcp.model.identification.Personne;

public class MissionDaoImpl extends JpaDaoImpl<Mission, Long> implements MissionDao, Serializable {

	private static final long serialVersionUID = -2609724288199083806L;

	@Override
	public Mission readSaisieByPersonne(Personne personne) {
		try{
			return entityManager.createQuery("SELECT mission FROM Mission mission WHERE EXISTS "
					+ "( SELECT dm FROM DossierMission dm WHERE dm.dernierTraitement.statut.code = :statutId AND dm.dernierTraitement.operation.creePar = :personne)"
					, clazz)
					.setParameter("personne", personne)
					.setParameter("statutId", Code.STATUT_SAISIE)
					.getSingleResult();
		}catch(NoResultException e){
			return null;
		}
		
	}

}
