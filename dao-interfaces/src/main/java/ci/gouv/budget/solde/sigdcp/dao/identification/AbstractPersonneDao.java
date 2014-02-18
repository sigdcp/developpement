package ci.gouv.budget.solde.sigdcp.dao.identification;

import ci.gouv.budget.solde.sigdcp.dao.DataAccessObject;
import ci.gouv.budget.solde.sigdcp.model.identification.Personne;

public interface AbstractPersonneDao<PERSONNE extends Personne> extends DataAccessObject<PERSONNE,Long> {

	/**
	 * Retourne la liste des inscriptions pas encore validées si valide est faux.
	 * Retourne la liste des personnes acceptées si valide est vrai
	 * @param valide
	 * @return liste des bénéficiaires
	 */
	//Collection<PERSONNE> findByValide(Boolean valide);

}
 