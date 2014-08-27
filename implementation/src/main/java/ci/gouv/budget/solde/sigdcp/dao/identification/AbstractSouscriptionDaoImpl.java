package ci.gouv.budget.solde.sigdcp.dao.identification;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

import ci.gouv.budget.solde.sigdcp.dao.JpaDaoImpl;
import ci.gouv.budget.solde.sigdcp.model.identification.Souscription;

public abstract class AbstractSouscriptionDaoImpl<SOUSCRIPTION extends Souscription> extends JpaDaoImpl<SOUSCRIPTION, String> implements AbstractSouscriptionDao<SOUSCRIPTION> , Serializable {

	private static final long serialVersionUID = -2609724288199083806L;

	@Override
	public Collection<SOUSCRIPTION> readByDateValidation(Date date) {
		return entityManager.createQuery("SELECT souscription FROM Souscription souscription WHERE souscription.dateValidation = :dateValidation", clazz)
				.setParameter("dateValidation", date)
				.getResultList();
	}

	@Override
	public Collection<SOUSCRIPTION> readDateValidationIsNullByTypePersonneId(String typePersonneId) {
		return entityManager.createQuery("SELECT souscription FROM Souscription souscription WHERE souscription.dateValidation IS NULL "
				+ " AND inscription.personneInfos.type.code = :typePersonneId ", clazz)
				.setParameter("typePersonneId", typePersonneId)
				.getResultList();
	}
	
	@Override
	public Collection<SOUSCRIPTION> readDateValidationIsNull() {
		return entityManager.createQuery("SELECT souscription FROM Souscription souscription WHERE souscription.dateValidation IS NULL ", clazz)
				.getResultList();
	}
	
	
	
}
