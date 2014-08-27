package ci.gouv.budget.solde.sigdcp.dao.prestation;

import java.io.Serializable;
import java.util.Collection;

import ci.gouv.budget.solde.sigdcp.dao.traitement.TraitableDaoImpl;
import ci.gouv.budget.solde.sigdcp.model.prestation.DemandeCotationMission;

public class DemandeCotationMissionDaoImpl extends TraitableDaoImpl<DemandeCotationMission, Long> implements DemandeCotationMissionDao, Serializable {

	private static final long serialVersionUID = -1425013722199012221L;

	@Override
	public Collection<DemandeCotationMission> readACommander() {
		return entityManager.createQuery("SELECT dcm FROM DemandeCotationMission dcm"
				+ " WHERE EXISTS ( SELECT pdcm FROM PrestataireDemandeCotationMission pdcm WHERE pdcm.id.demandeCotationMissionId=dcm.id AND pdcm.fichier IS NOT NULL "
				+ " AND NOT EXISTS ( SELECT cmd FROM CommandeTitreTransport cmd) )"
				, clazz)
				.getResultList();
	}

	
}
 