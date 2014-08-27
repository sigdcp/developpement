package ci.gouv.budget.solde.sigdcp.dao.identification;

import java.io.Serializable;
import java.util.Collection;

import ci.gouv.budget.solde.sigdcp.dao.JpaDaoImpl;
import ci.gouv.budget.solde.sigdcp.model.identification.Fonction;

public class FonctionDaoImpl extends JpaDaoImpl<Fonction, String> implements FonctionDao , Serializable {

	private static final long serialVersionUID = -2609724288199083806L;

	@Override
	public Collection<Fonction> readInGroupeMissions() {
		return entityManager.createQuery("SELECT fonction FROM Fonction fonction WHERE EXISTS ("
				+ " SELECT groupe FROM GroupeMission groupe WHERE fonction MEMBER OF groupe.fonctions"
				+ ")", clazz)
				.getResultList();
	}
	

}  