package ci.gouv.budget.solde.sigdcp.service.dossier;

import java.io.Serializable;
import java.util.Collection;

import javax.ejb.Stateless;
import javax.inject.Inject;

import ci.gouv.budget.solde.sigdcp.dao.dossier.PieceJustificativeDao;
import ci.gouv.budget.solde.sigdcp.model.dossier.Dossier;
import ci.gouv.budget.solde.sigdcp.model.dossier.PieceJustificative;
import ci.gouv.budget.solde.sigdcp.model.dossier.TypePieceJustificative;
import ci.gouv.budget.solde.sigdcp.service.DefaultServiceImpl;

@Stateless
public class PieceJustificativeServiceImpl extends DefaultServiceImpl<PieceJustificative, Long> implements PieceJustificativeService , Serializable {

	private static final long serialVersionUID = -7601857525393731774L;
	
	@Inject
	public PieceJustificativeServiceImpl(PieceJustificativeDao dao) {
		super(dao);
	}
	
	@Override
	public void creer(PieceJustificative pieceJustificative) {
		dao.create(pieceJustificative);
	}

	@Override
	public Collection<PieceJustificative> findByDossier(Dossier dossier) {
		return ((PieceJustificativeDao)dao).readByDossier(dossier);
	}

	@Override
	public Collection<PieceJustificative> findByDossierByType(Dossier dossier,TypePieceJustificative type) {
		return ((PieceJustificativeDao)dao).readByDossierByType(dossier, type);
	}

}
