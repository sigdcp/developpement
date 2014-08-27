package ci.gouv.budget.solde.sigdcp.dao.dossier;

import java.util.Collection;

import ci.gouv.budget.solde.sigdcp.model.identification.Fonction;
import ci.gouv.budget.solde.sigdcp.model.indemnite.GroupeMission;
import ci.gouv.budget.solde.sigdcp.model.indemnite.TypeClasseVoyage;

public interface GroupeMissionDao extends AbstractGroupeDao<GroupeMission> {

	GroupeMission readByFonction(Fonction fonction);

	Collection<GroupeMission> readByTypeClasseVoyage(TypeClasseVoyage typeClasseVoyage);

}
