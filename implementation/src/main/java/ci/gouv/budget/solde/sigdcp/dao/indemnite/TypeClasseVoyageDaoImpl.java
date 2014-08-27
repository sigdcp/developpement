package ci.gouv.budget.solde.sigdcp.dao.indemnite;

import java.io.Serializable;

import ci.gouv.budget.solde.sigdcp.dao.JpaDaoImpl;
import ci.gouv.budget.solde.sigdcp.model.indemnite.TypeClasseVoyage;

public class TypeClasseVoyageDaoImpl extends JpaDaoImpl<TypeClasseVoyage, String> implements TypeClasseVoyageDao, Serializable {

	private static final long serialVersionUID = -2609724288199083806L;

	/*@Override
	public Cercueil readByIndice(BigDecimal indice) {
		return entityManager.createQuery("SELECT c FROM Cercueil c WHERE EXISTS ( SELECT  itc From IndemniteTrancheCercueil itc WHERE itc.cercueil=c AND :indice BETWEEN itc.intervalleMin AND  itc.intervalleMax)", clazz)
				.setParameter("indice", indice)
				.getSingleResult();
	}*/
	
}
 