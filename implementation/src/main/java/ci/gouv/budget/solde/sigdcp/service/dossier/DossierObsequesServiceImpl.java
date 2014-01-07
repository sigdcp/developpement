package ci.gouv.budget.solde.sigdcp.service.dossier;

import java.io.Serializable;

import javax.ejb.Stateless;
import javax.inject.Inject;

import ci.gouv.budget.solde.sigdcp.dao.dossier.DossierObsequesDao;
import ci.gouv.budget.solde.sigdcp.model.dossier.DossierObseques;

@Stateless
public class DossierObsequesServiceImpl extends AbstractDossierServiceImpl<DossierObseques> implements DossierObsequesService,Serializable {
	
	private static final long serialVersionUID = -7765679080076677680L;
	
	@Inject
	public DossierObsequesServiceImpl(DossierObsequesDao dao) {
		super(dao);
	}

}
