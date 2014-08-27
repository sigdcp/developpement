package ci.gouv.budget.solde.sigdcp.service.indemnite;

import java.util.Collection;

import ci.gouv.budget.solde.sigdcp.model.identification.Fonction;
import ci.gouv.budget.solde.sigdcp.model.identification.Grade;
import ci.gouv.budget.solde.sigdcp.model.identification.Profession;
import ci.gouv.budget.solde.sigdcp.model.indemnite.GroupeMission;
import ci.gouv.budget.solde.sigdcp.model.indemnite.TypeClasseVoyage;

public interface GroupeMissionService extends AbstractGroupeService<GroupeMission> {
	
	GroupeMission findByFonctionOrGrade(Fonction fonction,Grade grade);
	GroupeMission findByFonctionOrProfession(Fonction fonction,Profession profession);
	Collection<GroupeMission> findByTypeClasseVoyage(Collection<TypeClasseVoyage> typeClasseVoyages);
}
 