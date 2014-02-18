package ci.gouv.budget.solde.sigdcp.dao.identification;

import java.io.Serializable;

import ci.gouv.budget.solde.sigdcp.dao.JpaDaoImpl;
import ci.gouv.budget.solde.sigdcp.model.identification.Personne;

public abstract class AbstractPersonneDaoImpl<PERSONNE extends Personne> extends JpaDaoImpl<PERSONNE, Long> implements AbstractPersonneDao<PERSONNE> , Serializable {

	private static final long serialVersionUID = -2609724288199083806L;
	/*
	@Override
	public Collection<PERSONNE> findByValide(Boolean valide) {
		return entityManager.createQuery("SELECT personne FROM Personne personne WHERE personne.valide = :valide", clazz)
				.setParameter("valide", valide)
				.getResultList();
	}
	*/
	
}

