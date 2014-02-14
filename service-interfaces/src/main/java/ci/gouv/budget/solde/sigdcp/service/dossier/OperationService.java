package ci.gouv.budget.solde.sigdcp.service.dossier;

import java.util.Collection;

import ci.gouv.budget.solde.sigdcp.model.dossier.Dossier;
import ci.gouv.budget.solde.sigdcp.model.dossier.Operation;
import ci.gouv.budget.solde.sigdcp.model.identification.Personne;
import ci.gouv.budget.solde.sigdcp.service.AbstractService;

public interface OperationService extends AbstractService<Operation,Long> {

	void valider(String natureOperationCode,Collection<Dossier> dossiers,Personne effectueePar);
	
}
