package ci.gouv.budget.solde.sigdcp.service.prestation;

import ci.gouv.budget.solde.sigdcp.model.dossier.DossierObseques;
import ci.gouv.budget.solde.sigdcp.model.prestation.DemandeCotationObseque;
import ci.gouv.budget.solde.sigdcp.service.traitement.TraitableService;

public interface DemandeCotationObsequeService extends TraitableService<DemandeCotationObseque,Long> {
	
	DemandeCotationObseque nouveau(DossierObseques dossierObseques);
	 
	//void enregistrerDemandeCotationObseque(DossierObseques dossier, Collection<Prestataire> prestataires) throws ServiceException;
	
}
