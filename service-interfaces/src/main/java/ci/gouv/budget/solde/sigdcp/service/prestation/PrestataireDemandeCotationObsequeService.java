package ci.gouv.budget.solde.sigdcp.service.prestation;

import java.util.Collection;

import ci.gouv.budget.solde.sigdcp.model.prestation.PrestataireDemandeCotationObseque;
import ci.gouv.budget.solde.sigdcp.model.prestation.PrestataireDemandeCotationObsequeId;
import ci.gouv.budget.solde.sigdcp.service.AbstractService;

public interface PrestataireDemandeCotationObsequeService extends AbstractService<PrestataireDemandeCotationObseque,PrestataireDemandeCotationObsequeId> {
	
	PrestataireDemandeCotationObseque nouveau(PrestataireDemandeCotationObseque demande);
	Collection<PrestataireDemandeCotationObseque> findATraiter(String natureOperationCode);
	void enregistrer(PrestataireDemandeCotationObseque demande);
	void init(PrestataireDemandeCotationObseque demande);
	
	
}
