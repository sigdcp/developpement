package ci.gouv.budget.solde.sigdcp.dao.dossier;

import java.util.Collection;

import ci.gouv.budget.solde.sigdcp.model.dossier.Deplacement;
import ci.gouv.budget.solde.sigdcp.model.dossier.Dossier;
import ci.gouv.budget.solde.sigdcp.model.dossier.PieceJustificative;

public interface PieceJustificativeDao extends AbstractDocumentDao<PieceJustificative> {

	Collection<PieceJustificative> readByDossier(Dossier dossier);
	
	PieceJustificative readAdministrativeByDossier(Dossier dossier);
	
	Collection<PieceJustificative> readByDossierByTypeId(Dossier dossier,String typeId);
	
	Collection<PieceJustificative> readByDeplacementByTypeId(Deplacement deplacement,String typeId);
	
	Long countByDossierByTypePieceJustificativeId(Dossier dossier,String typeId);
	
}
