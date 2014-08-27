package ci.gouv.budget.solde.sigdcp.service.dossier;

import ci.gouv.budget.solde.sigdcp.model.traitement.Operation;
import ci.gouv.budget.solde.sigdcp.service.AbstractService;

public interface OperationService extends AbstractService<Operation,Long> {

	Operation creer(String natureId);
	
}
