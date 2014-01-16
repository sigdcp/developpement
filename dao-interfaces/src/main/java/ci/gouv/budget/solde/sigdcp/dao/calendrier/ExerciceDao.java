package ci.gouv.budget.solde.sigdcp.dao.calendrier;

import java.util.Collection;

import ci.gouv.budget.solde.sigdcp.dao.DataAccessObject;
import ci.gouv.budget.solde.sigdcp.model.calendrier.Exercice;

public interface ExerciceDao extends DataAccessObject<Exercice,Integer> {

	Collection<Exercice> readByStatut(Boolean statut);

}
