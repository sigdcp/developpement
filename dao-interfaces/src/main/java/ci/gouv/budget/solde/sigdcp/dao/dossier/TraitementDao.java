package ci.gouv.budget.solde.sigdcp.dao.dossier;

import java.util.Collection;

import ci.gouv.budget.solde.sigdcp.dao.DataAccessObject;
import ci.gouv.budget.solde.sigdcp.model.dossier.Dossier;
import ci.gouv.budget.solde.sigdcp.model.dossier.PieceProduite;
import ci.gouv.budget.solde.sigdcp.model.dossier.Traitement;
import ci.gouv.budget.solde.sigdcp.model.identification.AgentEtat;

public interface TraitementDao extends DataAccessObject<Traitement,Long> {

	Collection<Traitement> readAllByDossier(Dossier dossier);
	
	Collection<Traitement> readByAgentEtat(AgentEtat agentEtat);
	
	Traitement readByPieceProduite(PieceProduite pieceProduite);
	
	Collection<Traitement> readByPieceProduiteTypeId(String typePieceProduiteId);
	
	Collection<Traitement> readByCategorieDeplacementIdByTypePieceProduiteIdByRecent(String categorieDeplacementId,String typePieceProduiteId,Boolean recent);
}
