package ci.gouv.budget.solde.sigdcp.dao.calendrier;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;

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
			return entityManager.createQuery("SELECT mission FROM MissionExecutee mission WHERE mission.organisateur = :organisateur AND EXISTS "
					+ "( SELECT dm FROM DossierMission dm WHERE dm.traitable.dernierTraitement.operation.nature.code IN :noCode AND dm.deplacement = mission.deplacement)"
					, clazz)
					.setParameter("organisateur", personne)
					.setParameter("noCode", Arrays.asList(Code.NATURE_OPERATION_SAISIE,Code.NATURE_OPERATION_TRANSMISSION_SAISIE_A_BENEFICIAIRE,Code.NATURE_OPERATION_TRANSMISSION_SAISIE_A_ORGANISATEUR))
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
	
	@Override
	public Collection<MissionExecutee> readByPersonne(Personne personne) {
		return entityManager.createQuery("SELECT me FROM MissionExecutee me"
				+ " WHERE EXISTS ( SELECT td FROM TraitementDossier td WHERE td.dossier.deplacement = me.deplacement AND td.operation.effectuePar = :personne ) "
				, clazz)
				.setParameter("personne", personne)
				.getResultList();
	}

	@Override
	public Collection<MissionExecutee> readACoter() {
		return entityManager.createQuery("SELECT me FROM MissionExecutee me"
				+ " WHERE NOT EXISTS( SELECT dcm FROM DemandeCotationMission dcm WHERE dcm.mission=me) AND "
				+ "	EXISTS ( SELECT td FROM TraitementDossier td WHERE td.dossier.deplacement = me.deplacement AND td.operation.nature.code = :natureOperationCode AND td.statut.code = :statutId ) "
				, clazz)
				.setParameter("natureOperationCode", Code.NATURE_OPERATION_CONFORMITE)
				.setParameter("statutId",  Code.STATUT_ACCEPTE)
				.getResultList();
	}

}
