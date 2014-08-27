package ci.gouv.budget.solde.sigdcp.dao.dossier;

import java.io.Serializable;
import java.util.Collection;

import ci.gouv.budget.solde.sigdcp.dao.traitement.TraitableDaoImpl;
import ci.gouv.budget.solde.sigdcp.model.dossier.NatureDeplacement;
import ci.gouv.budget.solde.sigdcp.model.dossier.PieceProduite;

public class PieceProduiteDaoImpl extends TraitableDaoImpl<PieceProduite,Long> implements PieceProduiteDao, Serializable {

	private static final long serialVersionUID = -2609724288199083806L;

	@Override
	public Collection<PieceProduite> readByTypeId(String typeId) {
		return entityManager.createQuery("SELECT pp FROM PieceProduite pp WHERE pp.type.code = :pptid", clazz)
				.setParameter("pptid", typeId)
				.getResultList();
	}
	
	@Override
	public Collection<PieceProduite> readByCategorieIdByTypePieceId(String cdid, String typeId) {
		return entityManager.createQuery("SELECT pp FROM PieceProduite pp WHERE pp.type.code = :pptid AND EXISTS("
				+ " SELECT t FROM Traitement t WHERE t.pieceProduite = pp AND t.dossier.deplacement.nature.categorie.code = :cdid"
				+ ")", clazz)
				.setParameter("pptid", typeId).setParameter("cdid", cdid)
				.getResultList();
	}

	@Override
	public Collection<PieceProduite> readByNatureDeplacementsByNatureOperationIdByStatutId(Collection<NatureDeplacement> natureDeplacements, String natureOperationId, String statutId) {
		return null;
	}

}
 