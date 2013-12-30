package ci.gouv.budget.solde.sigdcp.dao.dossier;

import java.io.Serializable;
import java.util.Collection;

import ci.gouv.budget.solde.sigdcp.dao.JpaDaoImpl;
import ci.gouv.budget.solde.sigdcp.model.dossier.PieceJustificativeAFournir;

public class PieceJustificativeAFournirDaoImpl extends JpaDaoImpl<PieceJustificativeAFournir, Long> implements PieceJustificativeAFournirDao, Serializable {

	private static final long serialVersionUID = -2609724288199083806L;
	
	@Override
	public Collection<PieceJustificativeAFournir> readAllByNatureDeplacementId(String id) {
		return entityManager.createQuery("SELECT pj FROM PieceJustificativeAFournir pj WHERE pj.natureDeplacement.code = :ndId", clazz)
				.setParameter("ndId", id)
				.getResultList();
	}

}
 