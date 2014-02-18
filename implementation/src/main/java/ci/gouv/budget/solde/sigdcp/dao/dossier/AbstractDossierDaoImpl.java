package ci.gouv.budget.solde.sigdcp.dao.dossier;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import ci.gouv.budget.solde.sigdcp.dao.JpaDaoImpl;
import ci.gouv.budget.solde.sigdcp.model.dossier.Dossier;
import ci.gouv.budget.solde.sigdcp.model.dossier.NatureDeplacement;
import ci.gouv.budget.solde.sigdcp.model.dossier.Statut;
import ci.gouv.budget.solde.sigdcp.model.dossier.Traitement;
import ci.gouv.budget.solde.sigdcp.model.identification.AgentEtat;
import ci.gouv.budget.solde.sigdcp.model.identification.Personne;

public abstract class AbstractDossierDaoImpl<DOSSIER extends Dossier> extends JpaDaoImpl<DOSSIER, String> implements AbstractDossierDao<DOSSIER>, Serializable {

	private static final long serialVersionUID = -2609724288199083806L;
	
	@SuppressWarnings("unchecked")
	@Override
	public DOSSIER readSaisieByPersonneByNatureDeplacement(Personne personne, NatureDeplacement natureDeplacement) {
		/*try {
			return entityManager.createQuery("SELECT d FROM Dossier d "
					+ "WHERE "
					//+ "d.beneficiaire = :personne"
					//+ " AND "
					+ "d.deplacement.nature = :nature ("
					+ " SELECT t FROM Traitement t WHERE t.dossier = d AND t.auteur = :personne"
					+ ")"
					, clazz)
					.setParameter("personne", personne)
					.setParameter("nature", natureDeplacement)
					.getSingleResult();
		} catch (NoResultException e) {
			return null;
		} catch (NonUniqueResultException e) {
			throw e;
		}*/
		//TODO a changer. dossier connait son dernier traitement
		List<Traitement> traitements = entityManager.createQuery("SELECT t FROM Traitement t WHERE t.dossier.deplacement.nature = :nature AND t.operation.creePar = :personne ORDER BY t.operation.date ASC"
				, Traitement.class)
				.setParameter("personne", personne)
				.setParameter("nature", natureDeplacement)
				.setMaxResults(2)
				.getResultList();
		
		return (DOSSIER) (traitements.size()==1?traitements.get(0).getDossier():null);
	}
	
	@Override
	public Collection<DOSSIER> readByAgentEtat(AgentEtat agentEtat) {
		return entityManager.createQuery("SELECT d FROM Dossier d WHERE d.beneficiaire = :agentEtat", clazz)
				.setParameter("agentEtat", agentEtat)
				.getResultList();
	}
	
	@Override
	public Collection<DOSSIER> readByAgentEtatAndAyantDroit(AgentEtat agentEtat) {
		return entityManager.createQuery("SELECT d FROM Dossier d WHERE d.beneficiaire = :agentEtat or d.beneficiaire.ayantDroit = :agentEtat", clazz)
				.setParameter("agentEtat", agentEtat)
				.getResultList();
	}
	
	@Override
	public Collection<DOSSIER> readByStatut(Statut statut) {
		return readByStatutId(statut.getCode());
	}
	 
	@Override
	public Collection<DOSSIER> readByStatutId(String id) {
		return entityManager.createQuery("SELECT d FROM Dossier d WHERE d.dernierTraitement.statut.code = :code"
				, clazz)
				.setParameter("code", id)
				.getResultList();
	}
	
	@Override
	public Collection<DOSSIER> readByNatureDeplacement(NatureDeplacement natureDeplacement) {
		return entityManager.createQuery("SELECT d FROM Dossier d "
				+ "WHERE d.deplacement.nature = :nature"
				, clazz)
				.setParameter("nature", natureDeplacement)
				.getResultList();
	}
	
	@Override
	public Collection<DOSSIER> readByNatureDeplacementAndStatut(NatureDeplacement natureDeplacement, Statut statut) {
		/*return entityManager.createQuery("SELECT d FROM Dossier d "
				+ "WHERE d.deplacement.nature = :nature AND "
				+ "EXISTS("
				+ " SELECT t FROM Traitement t WHERE t.dossier = d AND t.statut = :statut GROUP BY t.dossier ORDER BY t.operation.date DESC"
				+ ")"
				, clazz)
				//.setParameter("nature", natureDeplacement)
				//.setParameter("statut", statut)
				.getResultList();
				*/
		
		return entityManager.createQuery("SELECT d FROM Dossier d WHERE d.deplacement.nature = :nature AND d.dernierTraitement.statut = :statut ORDER BY d.deplacement.dateCreation ASC"
				, clazz)
				.setParameter("nature", natureDeplacement)
				.setParameter("statut", statut)
				.getResultList();
	}
	
	@Override
	public Collection<DOSSIER> readByNatureDeplacementsByStatut(Collection<NatureDeplacement> natureDeplacements, Statut statut) {		
		return entityManager.createQuery("SELECT d FROM Dossier d WHERE d.deplacement.nature IN (:natureDeplacements) AND d.dernierTraitement.statut = :statut ORDER BY d.deplacement.dateCreation ASC"
				, clazz)
				.setParameter("natureDeplacements", natureDeplacements)
				.setParameter("statut", statut)
				.getResultList();
	}

}
 