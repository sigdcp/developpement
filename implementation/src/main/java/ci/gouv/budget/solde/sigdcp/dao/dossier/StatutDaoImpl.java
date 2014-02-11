package ci.gouv.budget.solde.sigdcp.dao.dossier;

import java.io.Serializable;
import java.util.List;

import ci.gouv.budget.solde.sigdcp.dao.JpaDaoImpl;
import ci.gouv.budget.solde.sigdcp.model.dossier.Dossier;
import ci.gouv.budget.solde.sigdcp.model.dossier.Statut;
import ci.gouv.budget.solde.sigdcp.model.dossier.Traitement;

public class StatutDaoImpl extends JpaDaoImpl<Statut, String> implements StatutDao, Serializable {

	private static final long serialVersionUID = -2609724288199083806L;

	@Override
	public Statut readCourantByDossier(Dossier dossier) {
		List<Traitement> traitements = entityManager.createQuery("SELECT t FROM Traitement t WHERE t.dossier = :dossier ORDER BY t.operation.date DESC"
				, Traitement.class)
				.setParameter("dossier", dossier)
				.setMaxResults(1)
				.getResultList();
		return traitements.isEmpty()?null:traitements.get(0).getStatut();
	}
		
	
}
