package ci.gouv.budget.solde.sigdcp.dao.dossier;

import java.io.Serializable;
import java.util.Collection;

import ci.gouv.budget.solde.sigdcp.model.dossier.DossierMission;
import ci.gouv.budget.solde.sigdcp.model.identification.Personne;

public class DossierMissionDaoImpl extends AbstractDossierDaoImpl<DossierMission> implements DossierMissionDao, Serializable {

	private static final long serialVersionUID = -2609724288199083806L;
	
	@Override
	public Collection<DossierMission> readSaisieByPointFocal(Personne personne) {
		return entityManager.createQuery("SELECT dm FROM DossierMission dm WHERE dm.dernierTraitement.operation.effectuePar = :personne ORDER BY t.operation.date ASC"
				, clazz)
				.setParameter("personne", personne)
				.getResultList();
	}

}
