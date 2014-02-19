package ci.gouv.budget.solde.sigdcp.dao.dossier;

import java.io.Serializable;

import ci.gouv.budget.solde.sigdcp.dao.JpaDaoImpl;
import ci.gouv.budget.solde.sigdcp.model.dossier.Deplacement;

public abstract class AbstractDeplacementDaoImpl<DEPLACEMENT extends Deplacement> extends JpaDaoImpl<DEPLACEMENT, Long> implements AbstractDeplacementDao<DEPLACEMENT>, Serializable {

	private static final long serialVersionUID = -2609724288199083806L;
	
	/*
	@Override
	public DOSSIER readWithPieceJustificative(String identifiant) {
		return entityManager.createQuery("SELECT d FROM Dossier d JOIN FETCH d.pieceJustificatives WHERE d.numero = :numero", clazz)
				.setParameter("numero", identifiant)
				.getSingleResult();
	}
	*/ 

}
  