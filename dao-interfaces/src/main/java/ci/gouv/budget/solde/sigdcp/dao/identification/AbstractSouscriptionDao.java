package ci.gouv.budget.solde.sigdcp.dao.identification;

import java.util.Collection;
import java.util.Date;

import ci.gouv.budget.solde.sigdcp.dao.DataAccessObject;
import ci.gouv.budget.solde.sigdcp.model.identification.Souscription;

public interface AbstractSouscriptionDao<SOUSCRIPTION extends Souscription> extends DataAccessObject<SOUSCRIPTION,String> {

	/**
	 * Retourne la liste des souscriptions par date de validation.
	 * @return liste des souscriptions
	 */
	Collection<SOUSCRIPTION> readByDateValidation(Date date);
	
	/**
	 * Retourne la liste des souscriptions pas encore validées
	 * @param typePersonneId
	 * @return
	 */
	Collection<SOUSCRIPTION> readDateValidationIsNullByTypePersonneId(String typePersonneId);
	
	Collection<SOUSCRIPTION> readDateValidationIsNull();
	
}
