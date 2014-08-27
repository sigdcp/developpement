package ci.gouv.budget.solde.sigdcp.dao.traitement;

import java.io.Serializable;
import java.util.Collection;

import ci.gouv.budget.solde.sigdcp.dao.traitement.TraitementDossierDao;
import ci.gouv.budget.solde.sigdcp.model.dossier.Dossier;
import ci.gouv.budget.solde.sigdcp.model.dossier.NatureDeplacement;
import ci.gouv.budget.solde.sigdcp.model.dossier.PieceProduite;
import ci.gouv.budget.solde.sigdcp.model.identification.AgentEtat;
import ci.gouv.budget.solde.sigdcp.model.traitement.Statut;
import ci.gouv.budget.solde.sigdcp.model.traitement.TraitementDossier;

public class TraitementDossierDaoImpl extends AbstractTraitementDaoImpl<TraitementDossier> implements TraitementDossierDao, Serializable {

	private static final long serialVersionUID = -2609724288199083806L;

	@Override
	public Collection<TraitementDossier> readByDossier(Dossier dossier) {
		return entityManager.createQuery("SELECT traitement FROM TraitementDossier traitement WHERE traitement.dossier = :dossier", clazz)
				.setParameter("dossier", dossier)
				.getResultList();
	}
	
	@Override
	public Collection<TraitementDossier> readByDossierByNatureOperationIdByStatutId(Dossier dossier,String natureOperationId,String statutId) {
		return entityManager.createQuery("SELECT traitement FROM TraitementDossier traitement WHERE traitement.dossier = :dossier AND traitement.operation.nature.code = :noid AND "
				+ "traitement.statut.code = :statutId", clazz)
				.setParameter("dossier", dossier)
				.setParameter("noid", natureOperationId)
				.setParameter("statutId", statutId)
				.getResultList();
	}
	
	@Override
	public Collection<TraitementDossier> readByAgentEtat(AgentEtat agentEtat) {
		return entityManager.createQuery("SELECT DISTINCT traitement FROM TraitementDossier traitement WHERE traitement.dossier.beneficiaire = :agentEtat", clazz)
				.setParameter("agentEtat", agentEtat)
				.getResultList();
	}
	
	@Override 
	public TraitementDossier readByPieceProduite(PieceProduite pieceProduite) {
		return entityManager.createQuery("SELECT traitement FROM Traitement traitementDossier WHERE traitement.pieceProduite = :pieceProduite", clazz)
				.setParameter("pieceProduite", pieceProduite)
				.getSingleResult();
	}
	
	@Override
	public Collection<TraitementDossier> readByCategorieDeplacementIdByTypePieceProduiteIdByRecent(
			String categorieDeplacementId, String typePieceProduiteId,
			Boolean recent) {
		return null;/*entityManager.createQuery("SELECT DISTINCT traitement FROM Traitement traitement WHERE traitement.pieceProduite.type.code = :typePieceProduiteId "
				+ "AND traitement.dossier.deplaement.nature.categorie.code = :categorieDeplacementId", clazz)
				.setParameter("pieceProduite", pieceProduite)
				.getSingleResult();*/
	}

	@Override
	public Collection<TraitementDossier> readByPieceProduiteTypeId(String typePieceProduiteId) {
		return entityManager.createQuery("SELECT traitement FROM Traitement traitementDossier WHERE traitement.pieceProduite.type.code = :tppid", clazz)
				.setParameter("tppid", typePieceProduiteId)
				.getResultList();
	}
	
	@Override
	public Collection<TraitementDossier> readByNatureDeplacementByStatut(NatureDeplacement natureDeplacement, Statut statut) {
		return entityManager.createQuery("SELECT traitement FROM Traitement traitementDossier WHERE traitement.dossier.deplacement.nature = :nature AND traitement.statut = :statut", clazz)
				.setParameter("nature", natureDeplacement)
				.setParameter("statut", statut)
				.getResultList();
	}
	
}
