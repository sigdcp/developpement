package ci.gouv.budget.solde.sigdcp.dao.dossier;

import java.io.Serializable;
import java.util.Collection;

import ci.gouv.budget.solde.sigdcp.dao.JpaDaoImpl;
import ci.gouv.budget.solde.sigdcp.model.dossier.Dossier;
import ci.gouv.budget.solde.sigdcp.model.dossier.PieceProduite;
import ci.gouv.budget.solde.sigdcp.model.dossier.Traitement;

public class TraitementDaoImpl extends JpaDaoImpl<Traitement, Long> implements TraitementDao, Serializable {

	private static final long serialVersionUID = -2609724288199083806L;

	@Override
	public Collection<Traitement> readAllByDossier(Dossier dossier) {
		return entityManager.createQuery("SELECT traitement FROM Traitement traitement WHERE traitement.dossier = :dossier", clazz)
				.setParameter("dossier", dossier)
				.getResultList();
	}
	
	@Override 
	public Traitement readByPieceProduite(PieceProduite pieceProduite) {
		return entityManager.createQuery("SELECT traitement FROM Traitement traitement WHERE traitement.pieceProduite = :pieceProduite", clazz)
				.setParameter("pieceProduite", pieceProduite)
				.getSingleResult();
	}
	
	@Override
	public Collection<Traitement> readByCategorieDeplacementIdByTypePieceProduiteIdByRecent(
			String categorieDeplacementId, String typePieceProduiteId,
			Boolean recent) {
		return null;/*entityManager.createQuery("SELECT DISTINCT traitement FROM Traitement traitement WHERE traitement.pieceProduite.type.code = :typePieceProduiteId "
				+ "AND traitement.dossier.deplaement.nature.categorie.code = :categorieDeplacementId", clazz)
				.setParameter("pieceProduite", pieceProduite)
				.getSingleResult();*/
	}

	@Override
	public Collection<Traitement> readByPieceProduiteTypeId(String typePieceProduiteId) {
		return entityManager.createQuery("SELECT traitement FROM Traitement traitement WHERE traitement.pieceProduite.type.code = :tppid", clazz)
				.setParameter("tppid", typePieceProduiteId)
				.getResultList();
	}
	
}
