package ci.gouv.budget.solde.sigdcp.service.dossier;

import java.io.Serializable;

import javax.ejb.Stateless;
import javax.inject.Inject;

import ci.gouv.budget.solde.sigdcp.dao.dossier.DossierDao;
import ci.gouv.budget.solde.sigdcp.model.dossier.Dossier;

@Stateless
public class DossierServiceImpl extends AbstractDossierServiceImpl<Dossier> implements DossierService,Serializable {
	
	private static final long serialVersionUID = -7765679080076677680L;
	
	
	@Inject
	public DossierServiceImpl(DossierDao dao) {
		super(dao);
	}
	
	
}
