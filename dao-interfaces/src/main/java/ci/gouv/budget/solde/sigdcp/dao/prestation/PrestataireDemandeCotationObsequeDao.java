package ci.gouv.budget.solde.sigdcp.dao.prestation;

import java.util.Collection;

import ci.gouv.budget.solde.sigdcp.dao.DataAccessObject;
import ci.gouv.budget.solde.sigdcp.model.prestation.PrestataireDemandeCotationObseque;
import ci.gouv.budget.solde.sigdcp.model.prestation.PrestataireDemandeCotationObsequeId;

public interface PrestataireDemandeCotationObsequeDao extends DataAccessObject<PrestataireDemandeCotationObseque,PrestataireDemandeCotationObsequeId> {

	Collection<PrestataireDemandeCotationObseque> readByDemandeId(Long demandeId);
	Collection<PrestataireDemandeCotationObseque> readAllByPrestataireId(Long prestataireId);
	Collection<PrestataireDemandeCotationObseque> readATraiterByPrestataireId(Long prestataireId);
	Collection<PrestataireDemandeCotationObseque> readATraiter();
}
