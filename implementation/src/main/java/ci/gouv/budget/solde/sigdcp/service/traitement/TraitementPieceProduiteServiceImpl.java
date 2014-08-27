package ci.gouv.budget.solde.sigdcp.service.traitement;

import java.io.Serializable;

import javax.inject.Inject;

import ci.gouv.budget.solde.sigdcp.dao.DynamicEnumerationDao;
import ci.gouv.budget.solde.sigdcp.dao.dossier.PieceProduiteDao;
import ci.gouv.budget.solde.sigdcp.dao.traitement.TraitementPieceProduiteDao;
import ci.gouv.budget.solde.sigdcp.model.dossier.PieceProduite;
import ci.gouv.budget.solde.sigdcp.model.dossier.ValidationType;
import ci.gouv.budget.solde.sigdcp.model.traitement.Operation;
import ci.gouv.budget.solde.sigdcp.model.traitement.Statut;
import ci.gouv.budget.solde.sigdcp.model.traitement.TraitementPieceProduite;

public class TraitementPieceProduiteServiceImpl extends AbstractTraitementServiceImpl<TraitementPieceProduite> implements TraitementPieceProduiteService , Serializable {

	private static final long serialVersionUID = -7601857525393731774L;
	
	@Inject private DynamicEnumerationDao dynamicEnumerationDao;
	@Inject private PieceProduiteDao pieceProduiteDao;
	
	@Inject
	public TraitementPieceProduiteServiceImpl(TraitementPieceProduiteDao dao) {
		super(dao);
	}
	
	@Override
	public TraitementPieceProduite creer(Operation operation, PieceProduite pieceProduite,TraitementPieceProduite traitement, String statutId) {
		if(traitement==null){
			traitement = new TraitementPieceProduite();
			traitement.setValidationType(ValidationType.ACCEPTER);
		}
		//traitement = new TraitementBordereau(operation,dynamicEnumerationDao.readByClass(Statut.class, statutId),bordereauTransmission); 
		traitement.setOperation(operation);
		traitement.setStatut(dynamicEnumerationDao.readByClass(Statut.class, statutId));
		traitement.setPieceProduite(pieceProduite);
		((TraitementPieceProduiteDao)dao).create(traitement);
		pieceProduite.getTraitable().setDernierTraitement(traitement);
		pieceProduiteDao.update(pieceProduite);
		return traitement;
	}

}
