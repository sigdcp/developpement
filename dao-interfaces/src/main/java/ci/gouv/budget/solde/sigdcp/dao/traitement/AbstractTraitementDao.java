package ci.gouv.budget.solde.sigdcp.dao.traitement;

import java.util.Collection;

import ci.gouv.budget.solde.sigdcp.dao.DataAccessObject;
import ci.gouv.budget.solde.sigdcp.model.identification.Personne;
import ci.gouv.budget.solde.sigdcp.model.traitement.Traitement;

public interface AbstractTraitementDao<TRAITEMENT extends Traitement> extends DataAccessObject<TRAITEMENT,Long> {

	Collection<TRAITEMENT> readByEffectuePar(Personne personne);
	
}
