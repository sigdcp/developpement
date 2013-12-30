package ci.gouv.budget.solde.sigdcp.service.dossier;

import java.util.List;

import ci.gouv.budget.solde.sigdcp.model.dossier.BordereauTransmission;
import ci.gouv.budget.solde.sigdcp.model.dossier.NatureDeplacement;
import ci.gouv.budget.solde.sigdcp.service.AbstractService;
import ci.gouv.budget.solde.sigdcp.service.ServiceException;

public interface BordereauTransmissionService extends AbstractService<BordereauTransmission,Long> {

	
	public void creer(BordereauTransmission bordereauTransmission) throws ServiceException ;
	
	public void valider(BordereauTransmission bordereauTransmission) throws ServiceException ;
	
	public List<BordereauTransmission> lister(NatureDeplacement natureDeplacement);
	
}
