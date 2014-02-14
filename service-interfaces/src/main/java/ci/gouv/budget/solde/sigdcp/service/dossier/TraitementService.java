package ci.gouv.budget.solde.sigdcp.service.dossier;

import java.util.Collection;

import ci.gouv.budget.solde.sigdcp.model.dossier.Dossier;
import ci.gouv.budget.solde.sigdcp.model.dossier.NatureDeplacement;
import ci.gouv.budget.solde.sigdcp.model.dossier.PieceProduite;
import ci.gouv.budget.solde.sigdcp.model.dossier.Statut;
import ci.gouv.budget.solde.sigdcp.model.dossier.Traitement;
import ci.gouv.budget.solde.sigdcp.model.identification.AgentEtat;
import ci.gouv.budget.solde.sigdcp.service.AbstractService;

public interface TraitementService extends AbstractService<Traitement,Long> {
	
	Collection<Traitement> findByDossier(Dossier dossier);
	
	Collection<Traitement> findByAgentEtat(AgentEtat agentEtat);
	
	Traitement findByPieceProduite(PieceProduite pieceProduite);
	
	Collection<Traitement> findByPieceProduiteTypeId(String typePieceProduiteId);
	
	Collection<Traitement> findByNatureDeplacementByStatut(NatureDeplacement natureDeplacement,Statut statut);
	
	/**
	 * Ramene les traitements les plus récents
	 * @param categorieDeplacementId
	 * @param typePieceProduiteId
	 * @return
	 */
	//Collection<Traitement> findByCategorieDeplacementIdByTypePieceProduiteId(String categorieDeplacementId,String typePieceProduiteId);
}
