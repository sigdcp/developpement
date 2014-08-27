package ci.gouv.budget.solde.sigdcp.service.identification;

import java.io.Serializable;
import java.util.Collection;

import javax.inject.Inject;

import ci.gouv.budget.solde.sigdcp.dao.identification.SouscriptionGestionnaireCarteSotraDao;
import ci.gouv.budget.solde.sigdcp.model.identification.souscription.SouscriptionGestionnaireCarteSotra;
import ci.gouv.budget.solde.sigdcp.service.ServiceException;

public class SouscriptionGestionnaireCarteSotraServiceImpl extends AbstractSouscriptionServiceImpl<SouscriptionGestionnaireCarteSotra> implements SouscriptionGestionnaireCarteSotraService,Serializable {
	
	private static final long serialVersionUID = 1170771216036513138L;
	
	@Inject
	public SouscriptionGestionnaireCarteSotraServiceImpl(SouscriptionGestionnaireCarteSotraDao dao) {
		super(dao);
	}

	@Override
	public void souscrire(SouscriptionGestionnaireCarteSotra souscription)throws ServiceException {
	
	}
 
	@Override
	public void accepter(SouscriptionGestionnaireCarteSotra souscription)throws ServiceException {
			
	}

	@Override
	public void accepter(
			Collection<SouscriptionGestionnaireCarteSotra> souscriptions)
			throws ServiceException {
		
		
	}

	@Override
	public void rejeter(SouscriptionGestionnaireCarteSotra souscription)
			throws ServiceException {
		
		
	}

	@Override
	public void rejeter(
			Collection<SouscriptionGestionnaireCarteSotra> souscriptions)
			throws ServiceException {
		
		
	}

	
	
	

}
