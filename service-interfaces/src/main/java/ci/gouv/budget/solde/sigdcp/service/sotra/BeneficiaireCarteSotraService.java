package ci.gouv.budget.solde.sigdcp.service.sotra;

import java.util.Collection;

import ci.gouv.budget.solde.sigdcp.model.identification.DelegueSotra;
import ci.gouv.budget.solde.sigdcp.model.sotra.BeneficiaireCarteSotra;
import ci.gouv.budget.solde.sigdcp.service.AbstractService;
import ci.gouv.budget.solde.sigdcp.service.ServiceException;

public interface BeneficiaireCarteSotraService extends AbstractService<BeneficiaireCarteSotra,Long> {

	void adherer(DelegueSotra delegueSotra) throws ServiceException ;
	
	void annulerAdhesion() throws ServiceException ;
	
	void validerInscription(Collection<BeneficiaireCarteSotra> adherentCarteSotras) throws ServiceException ;
	
	Boolean estAdherent() throws ServiceException ;
	
	/**
	 * La liste des adherents a la liste de base du delegue sotra
	 * @return
	 */
	Collection<BeneficiaireCarteSotra> findAdherents();
	
	Collection<BeneficiaireCarteSotra> findByValide(Boolean valide);
}
