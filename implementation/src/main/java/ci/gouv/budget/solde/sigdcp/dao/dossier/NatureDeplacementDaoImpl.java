package ci.gouv.budget.solde.sigdcp.dao.dossier;

import java.io.Serializable;
import java.util.Collection;

import ci.gouv.budget.solde.sigdcp.dao.JpaDaoImpl;
import ci.gouv.budget.solde.sigdcp.model.dossier.NatureDeplacement;

public class NatureDeplacementDaoImpl extends JpaDaoImpl<NatureDeplacement, String> implements NatureDeplacementDao, Serializable {

	private static final long serialVersionUID = -2609724288199083806L;
	
	@Override
	public Collection<NatureDeplacement> readByCategorieId(String categoriId) {
		return entityManager.createQuery("SELECT nd FROM NatureDeplacement nd WHERE nd.categorie.code = :categorieId", clazz)
				.setParameter("categorieId", categoriId)
				.getResultList();
	}
	
	/*
	@Override
	public NatureDeplacement readWithPieceJustificativeAFournir(String identifiant) {
		return entityManager.createQuery("SELECT nd FROM NatureDeplacement nd JOIN FETCH nd.pieceJustificatives WHERE nd.code = :code", clazz)
				.setParameter("code", identifiant)
				.getSingleResult();
	}
	
	@Override
	public Collection<NatureDeplacement> findAllWithPieceJustificativeAFournir() {
		return entityManager.createQuery("SELECT DISTINCT nd FROM NatureDeplacement nd JOIN FETCH nd.pieceJustificatives", clazz)
				.getResultList();
	}
	*/

}
