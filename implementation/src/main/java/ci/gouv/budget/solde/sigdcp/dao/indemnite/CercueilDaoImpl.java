package ci.gouv.budget.solde.sigdcp.dao.indemnite;

import java.io.Serializable;
import java.math.BigDecimal;

import ci.gouv.budget.solde.sigdcp.dao.JpaDaoImpl;
import ci.gouv.budget.solde.sigdcp.model.indemnite.Cercueil;

public class CercueilDaoImpl extends JpaDaoImpl<Cercueil, String> implements CercueilDao, Serializable {

	private static final long serialVersionUID = -2609724288199083806L;

	@Override
	public Cercueil readByIndice(BigDecimal indice) {
		return entityManager.createQuery("SELECT c FROM Cercueil c WHERE EXISTS ( SELECT  itc From IndemniteTrancheCercueil itc WHERE itc.cercueil=c AND :indice BETWEEN itc.intervalleMin AND  itc.intervalleMax)", clazz)
				.setParameter("indice", indice)
				.getSingleResult();
	}
	
}
 