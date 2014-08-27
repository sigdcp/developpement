package ci.gouv.budget.solde.sigdcp.service.dossier;

import ci.gouv.budget.solde.sigdcp.model.traitement.NatureOperation;
import ci.gouv.budget.solde.sigdcp.model.traitement.NatureOperationDto;
import ci.gouv.budget.solde.sigdcp.service.AbstractService;

public interface NatureOperationService extends AbstractService<NatureOperation,String> {

	NatureOperationDto findDtoById(String id);
}
