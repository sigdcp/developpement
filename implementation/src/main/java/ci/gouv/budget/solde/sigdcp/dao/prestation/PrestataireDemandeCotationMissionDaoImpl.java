package ci.gouv.budget.solde.sigdcp.dao.prestation;

import java.io.Serializable;
import java.util.Collection;

import ci.gouv.budget.solde.sigdcp.dao.JpaDaoImpl;
import ci.gouv.budget.solde.sigdcp.model.prestation.PrestataireDemandeCotationMission;
import ci.gouv.budget.solde.sigdcp.model.prestation.PrestataireDemandeCotationMissionId;

public class PrestataireDemandeCotationMissionDaoImpl extends JpaDaoImpl<PrestataireDemandeCotationMission, PrestataireDemandeCotationMissionId> implements PrestataireDemandeCotationMissionDao, Serializable {

	private static final long serialVersionUID = -1425013722199012221L;

	@Override
	public Collection<PrestataireDemandeCotationMission> readByDemandeId(Long demandeId) {
		return entityManager.createQuery("SELECT pd FROM PrestataireDemandeCotationMission pd WHERE pd.id.demandeCotationMissionId = :demandeId ", clazz)
				.setParameter("demandeId", demandeId)
				.getResultList();
	}

	@Override
	public Collection<PrestataireDemandeCotationMission> readAllByPrestataireId(Long prestataireId) {
		return entityManager.createQuery("SELECT pd FROM PrestataireDemandeCotationMission pd WHERE pd.id.prestataireId = :prestataireId ", clazz)
				.setParameter("prestataireId", prestataireId)
				.getResultList();
	}

	@Override
	public Collection<PrestataireDemandeCotationMission> readATraiterByPrestataireId(Long prestataireId) {
		return entityManager.createQuery("SELECT pd FROM PrestataireDemandeCotationMission pd WHERE pd.id.prestataireId = :prestataireId AND pd.fichier IS NULL ", clazz)
				.setParameter("prestataireId", prestataireId)
				.getResultList();
	}

	@Override
	public Collection<PrestataireDemandeCotationMission> readATraiter() {
		return entityManager.createQuery("SELECT pd FROM PrestataireDemandeCotationMission pd WHERE pd.fichier IS NOT NULL AND NOT EXISTS ("
				+ " SELECT cmd FROM CommandeTitreTransport cmd WHERE cmd.prestataireDemandeCotationMission=pd) ", clazz)
				.getResultList();
	}

	

}
 