package ci.gouv.budget.solde.sigdcp.dao.indemnite;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.NoResultException;

import ci.gouv.budget.solde.sigdcp.dao.dossier.GroupeMissionDao;
import ci.gouv.budget.solde.sigdcp.model.identification.Fonction;
import ci.gouv.budget.solde.sigdcp.model.indemnite.GroupeMission;
import ci.gouv.budget.solde.sigdcp.model.indemnite.TypeClasseVoyage;

public class GroupeMissionDaoImpl extends AbstractGroupeDaoImpl<GroupeMission> implements GroupeMissionDao, Serializable {

	private static final long serialVersionUID = -2609724288199083806L;

	@Override
	public GroupeMission readByFonction(Fonction fonction) {
		try {
			return entityManager.createQuery("SELECT groupe FROM GroupeMission groupe WHERE :fonction MEMBER OF groupe.fonctions", clazz)
					.setParameter("fonction", fonction)
					.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	@Override
	public Collection<GroupeMission> readByTypeClasseVoyage(TypeClasseVoyage typeClasseVoyage) {
		return entityManager.createQuery("SELECT g FROM GroupeMission g WHERE g.classeVoyage = :typeClasseVoyage ",	clazz)
				.setParameter("typeClasseVoyage", typeClasseVoyage)
				.getResultList();
	}

	
}
