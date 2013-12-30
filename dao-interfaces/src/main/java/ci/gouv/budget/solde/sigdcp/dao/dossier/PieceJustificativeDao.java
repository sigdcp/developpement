package ci.gouv.budget.solde.sigdcp.dao.dossier;

import java.util.Collection;

import ci.gouv.budget.solde.sigdcp.dao.DataAccessObject;
import ci.gouv.budget.solde.sigdcp.model.dossier.Dossier;
import ci.gouv.budget.solde.sigdcp.model.dossier.PieceJustificative;
import ci.gouv.budget.solde.sigdcp.model.dossier.TypePieceJustificative;

public interface PieceJustificativeDao extends DataAccessObject<PieceJustificative,Long> {

	
	Collection<PieceJustificative> readByDossier(Dossier dossier);
	
	Collection<PieceJustificative> readByDossierByType(Dossier dossier,TypePieceJustificative type);

}
