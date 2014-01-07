package ci.gouv.budget.solde.sigdcp.service.dossier;

import java.io.Serializable;

import javax.ejb.Stateless;
import javax.inject.Inject;

import ci.gouv.budget.solde.sigdcp.dao.geographie.LocaliteDao;
import ci.gouv.budget.solde.sigdcp.model.geographie.Localite;
import ci.gouv.budget.solde.sigdcp.service.DefaultServiceImpl;

@Stateless
public class LocaliteServiceImpl extends DefaultServiceImpl<Localite,String> implements LocaliteService , Serializable {

	private static final long serialVersionUID = -7601857525393731774L;
	
	@Inject
	public LocaliteServiceImpl(LocaliteDao dao) {
		super(dao);
	}
	

}
