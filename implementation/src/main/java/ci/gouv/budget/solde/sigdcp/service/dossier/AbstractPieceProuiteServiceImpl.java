package ci.gouv.budget.solde.sigdcp.service.dossier;

import java.io.Serializable;
import java.util.Collection;

import javax.inject.Inject;

import ci.gouv.budget.solde.sigdcp.dao.dossier.AbstractPieceProduiteDao;
import ci.gouv.budget.solde.sigdcp.dao.traitement.OperationValidationConfigDao;
import ci.gouv.budget.solde.sigdcp.dao.traitement.TraitementPieceProduiteDao;
import ci.gouv.budget.solde.sigdcp.model.Code;
import ci.gouv.budget.solde.sigdcp.model.dossier.PieceProduite;
import ci.gouv.budget.solde.sigdcp.model.traitement.TraitementPieceProduite;
import ci.gouv.budget.solde.sigdcp.service.ServiceException;
import ci.gouv.budget.solde.sigdcp.service.sotra.TraitableServiceImpl;

public abstract class AbstractPieceProuiteServiceImpl<PIECE_PRODUITE extends PieceProduite> 
	extends TraitableServiceImpl<PIECE_PRODUITE,Long,TraitementPieceProduite> implements AbstractPieceProduiteService<PIECE_PRODUITE> , Serializable {

	private static final long serialVersionUID = -7601857525393731774L;
	
	@Inject protected OperationValidationConfigDao operationValidationConfigDao;
	@Inject protected TraitementPieceProduiteDao traitementPieceProduiteDao;
	
	public AbstractPieceProuiteServiceImpl(AbstractPieceProduiteDao<PIECE_PRODUITE> dao) {
		super(dao);
	}
	
	@Override
	public PIECE_PRODUITE findDemande(Long id, String natureOperationCode) throws ServiceException {
		serviceException("Ne doit pas etre sollicitée");
		return null;
	}
	
	@Override
	public Collection<PIECE_PRODUITE> findDemandes() {
		serviceException("Ne doit pas etre sollicitée");
		return null;
	}
	
	@Override
	public Collection<PIECE_PRODUITE> findDemandesSolde() {
		serviceException("Ne doit pas etre sollicitée");
		return null;
	}

	@Override
	protected void associerTraitement(PIECE_PRODUITE piece) {
		piece.getTraitable().getTraitement().setPieceProduite(piece);
	}

	@Override
	protected Object idValue(PIECE_PRODUITE piece) {
		return piece.getId();
	}

	@Override
	protected Collection<TraitementPieceProduite> historiqueTraitements(PIECE_PRODUITE piece) {
		return traitementPieceProduiteDao.readByPiece(piece);
	}

	@Override
	protected TraitementPieceProduite traitementInstance() {
		return new TraitementPieceProduite();
	}
	
	@Override
	protected void creation(PIECE_PRODUITE objetTraite) {
		super.creation(objetTraite);
		objetTraite.setDateEtablissement(tempsCourant());
	}
	
	/*
	@Override
	protected void afterCreation(PIECE_PRODUITE objetTraite) {
		super.afterCreation(objetTraite);
		objetTraite.setNumero(Code.NUMERO_PREFIX+objetTraite.getType().getCode()+"/"+objetTraite.getId());
		objetTraite = entityManager.merge(objetTraite);
	}*/
	
	@Override
	protected void validationAccepter(PIECE_PRODUITE piece) {
		super.validationAccepter(piece);
		if(piece.getTraitable().getNatureOperation().getPrecedent()==null){
			piece.setNumero(Code.NUMERO_PREFIX+piece.getType().getCode()+"/"+piece.getId());
			piece = entityManager.merge(piece);
		}
	}

}
