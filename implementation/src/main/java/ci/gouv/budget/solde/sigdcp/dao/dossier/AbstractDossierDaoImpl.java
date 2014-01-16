package ci.gouv.budget.solde.sigdcp.dao.dossier;

import java.io.Serializable;
import java.util.Collection;

import ci.gouv.budget.solde.sigdcp.dao.JpaDaoImpl;
import ci.gouv.budget.solde.sigdcp.model.dossier.Dossier;
import ci.gouv.budget.solde.sigdcp.model.dossier.NatureDeplacement;
import ci.gouv.budget.solde.sigdcp.model.dossier.Statut;

public abstract class AbstractDossierDaoImpl<DOSSIER extends Dossier> extends JpaDaoImpl<DOSSIER, String> implements AbstractDossierDao<DOSSIER>, Serializable {

	private static final long serialVersionUID = -2609724288199083806L;
	
	/*
	@Override
	public DOSSIER readWithPieceJustificative(String identifiant) {
		return entityManager.createQuery("SELECT d FROM Dossier d JOIN FETCH d.pieceJustificatives WHERE d.numero = :numero", clazz)
				.setParameter("numero", identifiant)
				.getSingleResult();
	}
	*/
	 
	@Override
	public Collection<DOSSIER> readByStatut(Statut statut) {
		return entityManager.createQuery("SELECT d FROM Dossier d "
				+ "WHERE EXISTS("
				+ "SELECT t FROM Traitement t WHERE t.dossier = d AND t.statut = :statut"
				+ ")"
				, clazz)
				.setParameter("statut", statut)
				.getResultList();
	}
	
	@Override
	public Collection<DOSSIER> readByNatureDeplacement(NatureDeplacement natureDeplacement) {
		return entityManager.createQuery("SELECT d FROM Dossier d "
				+ "WHERE d.deplacement.nature = :nature"
				, clazz)
				.setParameter("nature", natureDeplacement)
				.getResultList();
	}
	
	@Override
	public Collection<DOSSIER> readByNatureDeplacementAndStatut(NatureDeplacement natureDeplacement, Statut statut) {
		return entityManager.createQuery("SELECT d FROM Dossier d "
				+ "WHERE d.deplacement.nature = :nature"
				+ " AND  EXISTS("
				+ "SELECT t FROM Traitement t WHERE t.dossier = d AND t.statut = :statut"
				+ ")"
				, clazz)
				.setParameter("nature", natureDeplacement)
				.setParameter("statut", statut)
				.getResultList();
	}

}
 