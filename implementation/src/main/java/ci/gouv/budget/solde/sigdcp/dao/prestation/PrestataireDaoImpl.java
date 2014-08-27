package ci.gouv.budget.solde.sigdcp.dao.prestation;

import java.io.Serializable;
import java.util.Collection;

import ci.gouv.budget.solde.sigdcp.model.prestation.Prestataire;

public class PrestataireDaoImpl extends AbstractPrestataireDaoImpl<Prestataire> implements PrestataireDao , Serializable {

	private static final long serialVersionUID = -2609724288199083806L;

	@Override
	public Collection<Prestataire> readByType(String typePrestataire) {		
			return entityManager.createQuery("SELECT p FROM Prestataire p WHERE p.type.code = :typePrestataire", clazz)
					.setParameter("typePrestataire", typePrestataire)
					.getResultList();
		
	}

	
	
}

