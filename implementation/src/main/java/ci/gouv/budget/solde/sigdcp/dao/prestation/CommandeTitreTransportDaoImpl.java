package ci.gouv.budget.solde.sigdcp.dao.prestation;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.NoResultException;

import ci.gouv.budget.solde.sigdcp.model.Code;
import ci.gouv.budget.solde.sigdcp.model.calendrier.MissionExecutee;
import ci.gouv.budget.solde.sigdcp.model.prestation.CommandeTitreTransport;

public class CommandeTitreTransportDaoImpl extends AbstractCommandeDaoImpl<CommandeTitreTransport> implements CommandeTitreTransportDao , Serializable {

	private static final long serialVersionUID = -2609724288199083806L;

	@Override
	public CommandeTitreTransport readByMission(MissionExecutee mission) {
		try {
			return entityManager.createQuery("SELECT commande FROM CommandeTitreTransport commande WHERE commande.mission = :mission", clazz)
					.setParameter("mission", mission)
					.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	@Override
	public Collection<CommandeTitreTransport> readByPrestataire(Long prestataire) {
			/*return entityManager.createQuery("SELECT c FROM CommandeTitreTransport c WHERE "
					+ "EXISTS (SELECT pd FROM PrestataireDemandeCotationMission pd WHERE pd.id.prestataireId = :prestataire) "
					+ "AND EXISTS (SELECT t FROM TraitementPieceProduite t WHERE t=c.traitable.dernierTraitement AND t.operation.nature.code = :operation AND t.statut.code = :statut)", clazz)
					.setParameter("prestataire", prestataire)
					.setParameter("operation", Code.NATURE_OPERATION_VISA_BCTT)
					.setParameter("statut", Code.STATUT_ACCEPTE)
					.getResultList();*/
		return entityManager.createQuery("SELECT c FROM CommandeTitreTransport c WHERE c.traitable.dernierTraitement.operation.nature.code = :operation AND c.traitable.dernierTraitement.statut.code = :statut "
				+ "AND c.prestataireDemandeCotationMission.id.prestataireId = :prestataire", clazz)
				.setParameter("prestataire", prestataire)
				.setParameter("operation", Code.NATURE_OPERATION_VISA_BCTT)
				.setParameter("statut", Code.STATUT_ACCEPTE)
				.getResultList();
		
	}	
	
}

