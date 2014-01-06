package ci.gouv.budget.solde.sigdcp.dao.identification;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

import ci.gouv.budget.solde.sigdcp.dao.JpaDaoImpl;
import ci.gouv.budget.solde.sigdcp.model.identification.Inscription;

public class InscriptionDaoImpl extends JpaDaoImpl<Inscription, String> implements InscriptionDao , Serializable {

	private static final long serialVersionUID = -2609724288199083806L;

	@Override
	public Collection<Inscription> findByDateValidation(Date date) {
		return entityManager.createQuery("SELECT inscription FROM Inscription inscription WHERE inscription.dateValidation = :dateValidation", clazz)
				.setParameter("dateValidation", date)
				.getResultList();
	}

	@Override
	public Collection<Inscription> findByDateValidationIsNullByTypePersonneId(String typePersonneId) {
		return entityManager.createQuery("SELECT inscription FROM Inscription inscription WHERE inscription.dateValidation IS NULL "
				+ " AND inscription.personneInfos.type.code = :typePersonneId ", clazz)
				.setParameter("typePersonneId", typePersonneId)
				.getResultList();
	}
	
	@Override
	public Collection<Inscription> findByDateValidationIsNull() {
		return entityManager.createQuery("SELECT inscription FROM Inscription inscription WHERE inscription.dateValidation IS NULL ", clazz)
				.getResultList();
	}
	
	
	
}
