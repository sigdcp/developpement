package ci.gouv.budget.solde.sigdcp.dao.identification;

import java.util.Collection;
import java.util.Date;

import ci.gouv.budget.solde.sigdcp.dao.DataAccessObject;
import ci.gouv.budget.solde.sigdcp.model.identification.Inscription;

public interface InscriptionDao extends DataAccessObject<Inscription,String> {

	/**
	 * Retourne la liste des inscriptions par date de validation.
	 * @return liste des inscriptions
	 */
	Collection<Inscription> findByDateValidation(Date date);
	
	/**
	 * Retourne la liste des inscriptions pas encore validées
	 * @param typePersonneId
	 * @return
	 */
	Collection<Inscription> findByDateValidationIsNullByTypePersonneId(String typePersonneId);
	
	Collection<Inscription> findByDateValidationIsNull();
	
}
