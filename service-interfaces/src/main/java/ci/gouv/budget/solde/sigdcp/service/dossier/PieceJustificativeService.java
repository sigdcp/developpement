package ci.gouv.budget.solde.sigdcp.service.dossier;

import java.util.Collection;

import ci.gouv.budget.solde.sigdcp.model.dossier.Dossier;
import ci.gouv.budget.solde.sigdcp.model.dossier.PieceJustificative;
import ci.gouv.budget.solde.sigdcp.model.dossier.TypePieceJustificative;
import ci.gouv.budget.solde.sigdcp.service.AbstractService;

public interface PieceJustificativeService extends AbstractService<PieceJustificative,Long> {

	void creer(PieceJustificative pieceJustificative);
	
	Collection<PieceJustificative> findByDossier(Dossier dossier);
	
	Collection<PieceJustificative> findByDossierByType(Dossier dossier,TypePieceJustificative type);
	
}
