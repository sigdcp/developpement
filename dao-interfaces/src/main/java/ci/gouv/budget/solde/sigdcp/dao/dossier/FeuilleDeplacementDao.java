package ci.gouv.budget.solde.sigdcp.dao.dossier;

import java.util.Collection;

import ci.gouv.budget.solde.sigdcp.model.dossier.Dossier;
import ci.gouv.budget.solde.sigdcp.model.dossier.FeuilleDeplacement;
import ci.gouv.budget.solde.sigdcp.model.dossier.NatureDeplacement;

public interface FeuilleDeplacementDao extends AbstractPieceProduiteDao<FeuilleDeplacement> {

	Collection<FeuilleDeplacement> readByDernierTraitementIsNull(Collection<NatureDeplacement> natureDeplacements);
	FeuilleDeplacement readByDossier(Dossier dossier);
}
