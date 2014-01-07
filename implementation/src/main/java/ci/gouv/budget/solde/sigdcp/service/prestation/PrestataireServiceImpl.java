package ci.gouv.budget.solde.sigdcp.service.prestation;

import java.io.Serializable;

import javax.ejb.Stateless;
import javax.inject.Inject;

import ci.gouv.budget.solde.sigdcp.dao.prestation.PrestataireDao;
import ci.gouv.budget.solde.sigdcp.model.prestation.Prestataire;

@Stateless
public class PrestataireServiceImpl extends AbstractPrestataireServiceImpl<Prestataire> implements PrestataireService , Serializable {

	private static final long serialVersionUID = -7601857525393731774L;
	
	@Inject
	public PrestataireServiceImpl(PrestataireDao dao) {
		super(dao);
	}
	 

}

