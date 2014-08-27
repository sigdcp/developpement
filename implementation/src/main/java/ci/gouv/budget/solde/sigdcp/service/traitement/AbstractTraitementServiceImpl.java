package ci.gouv.budget.solde.sigdcp.service.traitement;

import java.io.Serializable;

import ci.gouv.budget.solde.sigdcp.dao.traitement.AbstractTraitementDao;
import ci.gouv.budget.solde.sigdcp.model.traitement.Traitement;
import ci.gouv.budget.solde.sigdcp.service.DefaultServiceImpl;

public abstract class AbstractTraitementServiceImpl<TRAITEMENT extends Traitement> extends DefaultServiceImpl<TRAITEMENT, Long> implements AbstractTraitementService<TRAITEMENT> , Serializable {

	private static final long serialVersionUID = -7601857525393731774L;
	
	public AbstractTraitementServiceImpl(AbstractTraitementDao<TRAITEMENT> dao) {
		super(dao);
	}

}
