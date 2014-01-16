package ci.gouv.budget.solde.sigdcp.dao.calendrier;

import java.io.Serializable;
import java.util.Collection;

import ci.gouv.budget.solde.sigdcp.dao.JpaDaoImpl;
import ci.gouv.budget.solde.sigdcp.model.calendrier.Exercice;

public class ExerciceDaoImpl extends JpaDaoImpl<Exercice, Integer> implements ExerciceDao, Serializable {

	private static final long serialVersionUID = -2609724288199083806L;

	@Override
	public Collection<Exercice> readByStatut(Boolean statut) {
		return entityManager.createQuery("SELECT exercice FROM Exercice exercice WHERE exercice.statut = :statut", clazz)
				.setParameter("statut", statut)
				.getResultList();
	}
	

}
