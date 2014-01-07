package ci.gouv.budget.solde.sigdcp.service.dossier;

import java.io.Serializable;

import javax.ejb.Stateless;
import javax.inject.Inject;

import ci.gouv.budget.solde.sigdcp.dao.dossier.DossierTransitDao;
import ci.gouv.budget.solde.sigdcp.model.dossier.DossierTransit;

@Stateless
public class DossierTransitServiceImpl extends AbstractDossierServiceImpl<DossierTransit> implements DossierTransitService,Serializable {
	
	private static final long serialVersionUID = -7765679080076677680L;
	
	@Inject
	public DossierTransitServiceImpl(DossierTransitDao dao) {
		super(dao);
	}

}
