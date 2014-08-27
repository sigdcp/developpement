package ci.gouv.budget.solde.sigdcp.dao.traitement;

import java.io.Serializable;
import java.util.Collection;

import ci.gouv.budget.solde.sigdcp.dao.JpaDaoImpl;
import ci.gouv.budget.solde.sigdcp.dao.traitement.AbstractTraitementDao;
import ci.gouv.budget.solde.sigdcp.model.identification.Personne;
import ci.gouv.budget.solde.sigdcp.model.traitement.Traitement;

public abstract class AbstractTraitementDaoImpl<TRAITEMENT extends Traitement> extends JpaDaoImpl<TRAITEMENT,Long> implements AbstractTraitementDao<TRAITEMENT>, Serializable {

	private static final long serialVersionUID = -2609724288199083806L;
	
	@Override
	public Collection<TRAITEMENT> readByEffectuePar(Personne effectuePar) {
		return entityManager.createQuery("SELECT traitement FROM Traitement traitement WHERE traitement.operation.effectuePar = :effectuePar", clazz)
				.setParameter("effectuePar", effectuePar)
				.getResultList();
	}
	
}
