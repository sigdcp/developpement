package ci.gouv.budget.solde.sigdcp.service.dossier;

import java.io.Serializable;

import javax.inject.Inject;

import ci.gouv.budget.solde.sigdcp.dao.dossier.DeplacementDao;
import ci.gouv.budget.solde.sigdcp.model.dossier.Deplacement;

public class DeplacementServiceImpl extends AbstractDeplacementServiceImpl<Deplacement> implements DeplacementService , Serializable {

	private static final long serialVersionUID = -7601857525393731774L;
	
	@Inject
	public DeplacementServiceImpl(DeplacementDao dao) {
		super(dao);
	}

}
