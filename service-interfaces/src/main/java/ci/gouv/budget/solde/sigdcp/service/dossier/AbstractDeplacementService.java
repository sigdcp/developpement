package ci.gouv.budget.solde.sigdcp.service.dossier;

import ci.gouv.budget.solde.sigdcp.model.dossier.Deplacement;
import ci.gouv.budget.solde.sigdcp.service.AbstractService;

public interface AbstractDeplacementService<DEPLACEMENT extends Deplacement> extends AbstractService<DEPLACEMENT,Long> {

	Deplacement creer(DEPLACEMENT deplacement);

}
