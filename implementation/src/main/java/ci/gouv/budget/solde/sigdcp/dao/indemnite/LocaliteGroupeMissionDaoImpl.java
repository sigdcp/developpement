package ci.gouv.budget.solde.sigdcp.dao.indemnite;

import java.io.Serializable;

import javax.persistence.NoResultException;

import ci.gouv.budget.solde.sigdcp.dao.JpaDaoImpl;
import ci.gouv.budget.solde.sigdcp.model.geographie.Localite;
import ci.gouv.budget.solde.sigdcp.model.indemnite.GroupeMission;
import ci.gouv.budget.solde.sigdcp.model.indemnite.LocaliteGroupeMission;
import ci.gouv.budget.solde.sigdcp.model.indemnite.LocaliteGroupeMissionId;

public class LocaliteGroupeMissionDaoImpl extends JpaDaoImpl<LocaliteGroupeMission, LocaliteGroupeMissionId> implements LocaliteGroupeMissionDao, Serializable {

	private static final long serialVersionUID = -2609724288199083806L;

	@Override
	public LocaliteGroupeMission readByLocaliteByGroupe(Localite localite, GroupeMission groupeMission) {
		try {
			return entityManager.createQuery("SELECT lgm FROM LocaliteGroupeMission lgm WHERE lgm.id.groupeId = :groupeId AND lgm.id.localiteId = :localiteId ", clazz)
					.setParameter("groupeId", groupeMission.getCode())
					.setParameter("localiteId", localite.getCode())
					.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

}
