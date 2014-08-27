package ci.gouv.budget.solde.sigdcp.dao.prestation;

import java.io.Serializable;
import java.util.Collection;

import ci.gouv.budget.solde.sigdcp.dao.JpaDaoImpl;
import ci.gouv.budget.solde.sigdcp.model.prestation.PrestataireDemandeCotationObseque;
import ci.gouv.budget.solde.sigdcp.model.prestation.PrestataireDemandeCotationObsequeId;

public class PrestataireDemandeCotationObsequeDaoImpl extends JpaDaoImpl<PrestataireDemandeCotationObseque, PrestataireDemandeCotationObsequeId> implements PrestataireDemandeCotationObsequeDao, Serializable {

	private static final long serialVersionUID = -1425013722199012221L;

	@Override
	public Collection<PrestataireDemandeCotationObseque> readByDemandeId(Long demandeId) {
		return entityManager.createQuery("SELECT pd FROM PrestataireDemandeCotationObseque pd WHERE pd.id.demandeCotationMissionId = :demandeId ", clazz)
				.setParameter("demandeId", demandeId)
				.getResultList();
	}

	@Override
	public Collection<PrestataireDemandeCotationObseque> readAllByPrestataireId(Long prestataireId) {
		return entityManager.createQuery("SELECT pd FROM PrestataireDemandeCotationObseque pd WHERE pd.id.prestataireId = :prestataireId ", clazz)
				.setParameter("prestataireId", prestataireId)
				.getResultList();
	}

	@Override
	public Collection<PrestataireDemandeCotationObseque> readATraiterByPrestataireId(Long prestataireId) {
		return entityManager.createQuery("SELECT pd FROM PrestataireDemandeCotationObseque pd WHERE pd.id.prestataireId = :prestataireId AND pd.montantTransport IS NULL AND pd.montantCercueil IS NULL ", clazz)
				.setParameter("prestataireId", prestataireId)
				.getResultList();
	}

	@Override
	public Collection<PrestataireDemandeCotationObseque> readATraiter() {
		return entityManager.createQuery("SELECT pd FROM PrestataireDemandeCotationObseque pd WHERE pd.montantTransport IS NOT NULL AND pd.montantCercueil IS NOT NULL AND NOT EXISTS ("
				+ " SELECT cmd FROM CommandeObseque cmd WHERE cmd.prestataireDemandeCotationObseque=pd) ", clazz)
				.getResultList();
	}

	

}
 