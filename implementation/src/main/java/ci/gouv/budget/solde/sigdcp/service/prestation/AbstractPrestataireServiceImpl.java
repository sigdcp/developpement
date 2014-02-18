package ci.gouv.budget.solde.sigdcp.service.prestation;

import java.io.Serializable;

import ci.gouv.budget.solde.sigdcp.dao.prestation.AbstractPrestataireDao;
import ci.gouv.budget.solde.sigdcp.model.prestation.Prestataire;
import ci.gouv.budget.solde.sigdcp.service.DefaultServiceImpl;

public abstract class AbstractPrestataireServiceImpl<PRESTATAIRE extends Prestataire> extends DefaultServiceImpl<PRESTATAIRE, Long> implements AbstractPrestataireService<PRESTATAIRE> , Serializable {

	private static final long serialVersionUID = -7601857525393731774L;
	
	public AbstractPrestataireServiceImpl(AbstractPrestataireDao<PRESTATAIRE> dao) {
		super(dao);
	}
	
	

}

