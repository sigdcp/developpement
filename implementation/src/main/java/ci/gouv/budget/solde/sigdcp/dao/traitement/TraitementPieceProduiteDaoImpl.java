package ci.gouv.budget.solde.sigdcp.dao.traitement;

import java.io.Serializable;
import java.util.Collection;

import ci.gouv.budget.solde.sigdcp.dao.traitement.TraitementPieceProduiteDao;
import ci.gouv.budget.solde.sigdcp.model.dossier.PieceProduite;
import ci.gouv.budget.solde.sigdcp.model.traitement.TraitementPieceProduite;

public class TraitementPieceProduiteDaoImpl extends AbstractTraitementDaoImpl<TraitementPieceProduite> implements TraitementPieceProduiteDao, Serializable {

	private static final long serialVersionUID = -2609724288199083806L;

	@Override
	public Collection<TraitementPieceProduite> readByPiece(PieceProduite piece) {
		return entityManager.createQuery("SELECT traitement FROM TraitementPieceProduite traitement WHERE traitement.pieceProduite = :piece ORDER BY traitement.operation.date DESC ", clazz)
				.setParameter("piece",piece)
				.getResultList();
	}
	
	@Override
	public Collection<TraitementPieceProduite> readByPieceProduiteByNatureOperationIdByStatutId(PieceProduite pieceProduite, String natureOperationId, String statutId) {
		return entityManager.createQuery("SELECT tpp FROM TraitementPieceProduite tpp WHERE tpp.pieceProduite = :pieceProduite AND tpp.operation.nature.code = :noid AND "
				+ "tpp.statut.code = :statutId", clazz)
				.setParameter("pieceProduite", pieceProduite)
				.setParameter("noid", natureOperationId)
				.setParameter("statutId", statutId)
				.getResultList();
	}
	
}
 