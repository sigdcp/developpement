package ci.gouv.budget.solde.sigdcp.service.dossier;

import java.io.Serializable;
import java.util.Collection;

import javax.ejb.Stateless;
import javax.inject.Inject;

import ci.gouv.budget.solde.sigdcp.dao.dossier.PieceJustificativeAFournirDao;
import ci.gouv.budget.solde.sigdcp.model.dossier.PieceJustificativeAFournir;
import ci.gouv.budget.solde.sigdcp.service.DefaultServiceImpl;

@Stateless
public class PieceJustificativeAFournirServiceImpl extends DefaultServiceImpl<PieceJustificativeAFournir, Long> implements PieceJustificativeAFournirService , Serializable {

	private static final long serialVersionUID = -7601857525393731774L;
	
	@Inject
	public PieceJustificativeAFournirServiceImpl(PieceJustificativeAFournirDao dao) {
		super(dao);
	}
	
	@Override
	public Collection<PieceJustificativeAFournir> findByNatureDeplacementId(String id) {
		return ((PieceJustificativeAFournirDao)dao).readAllByNatureDeplacementId(id);
	}

}
