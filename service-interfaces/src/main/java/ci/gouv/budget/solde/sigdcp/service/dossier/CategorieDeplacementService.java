package ci.gouv.budget.solde.sigdcp.service.dossier;

import java.math.BigDecimal;

import ci.gouv.budget.solde.sigdcp.model.dossier.BordereauTransmission;
import ci.gouv.budget.solde.sigdcp.model.dossier.CategorieDeplacement;
import ci.gouv.budget.solde.sigdcp.service.AbstractDynamicEnumerationService;
import ci.gouv.budget.solde.sigdcp.service.ServiceException;

public interface CategorieDeplacementService extends AbstractDynamicEnumerationService<CategorieDeplacement> {

	void mettreAJourDisponibleTresor(CategorieDeplacement categorieDeplacement,BigDecimal montantEnAjout) throws ServiceException;
	
	void debiterDisponible(CategorieDeplacement categorieDeplacement,BigDecimal montant);
	
	void crediterDisponible(CategorieDeplacement categorieDeplacement,BigDecimal montant);
	
	void mettreAJourDisponibleTresor(CategorieDeplacement categorieDeplacement,BordereauTransmission bordereauTransmission) throws ServiceException;
	
}
