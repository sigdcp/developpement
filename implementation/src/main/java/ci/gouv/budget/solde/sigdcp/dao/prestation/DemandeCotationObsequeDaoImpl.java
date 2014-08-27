package ci.gouv.budget.solde.sigdcp.dao.prestation;

import java.io.Serializable;
import java.util.Collection;

import ci.gouv.budget.solde.sigdcp.dao.traitement.TraitableDaoImpl;
import ci.gouv.budget.solde.sigdcp.model.prestation.DemandeCotationObseque;

public class DemandeCotationObsequeDaoImpl extends TraitableDaoImpl<DemandeCotationObseque, Long> implements DemandeCotationObsequeDao, Serializable {

	private static final long serialVersionUID = -1425013722199012221L;

	@Override
	public Collection<DemandeCotationObseque> readACommander() {
		return entityManager.createQuery("SELECT dco FROM DemandeCotationObseque dco"                    
				+ " WHERE EXISTS ( SELECT pdco FROM PrestataireDemandeCotationObseque pdco WHERE pdco.id.demandeCotationObsequeId=dco.id AND pdco.montantTransport IS NOT NULL AND pdco.montantCercueil IS NOT NULL "
				+ " AND NOT EXISTS ( SELECT cmd FROM CommandeObseque cmd WHERE cmd.prestataireDemandeCotationObseque=pdco) )"
				, clazz)
				.getResultList();
	}

}
 