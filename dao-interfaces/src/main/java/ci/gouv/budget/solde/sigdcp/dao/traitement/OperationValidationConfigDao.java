package ci.gouv.budget.solde.sigdcp.dao.traitement;

import java.util.Collection;

import ci.gouv.budget.solde.sigdcp.dao.DataAccessObject;
import ci.gouv.budget.solde.sigdcp.model.dossier.ValidationType;
import ci.gouv.budget.solde.sigdcp.model.traitement.OperationValidationConfig;

public interface OperationValidationConfigDao extends DataAccessObject<OperationValidationConfig,Long> {

	OperationValidationConfig readByNatureOperationIdByValidationType(String natureOperationId,ValidationType validationType);
	
	Collection<OperationValidationConfig> readByNatureOperationId(String natureOperationId);
	
	Long countByNatureOperationId(String natureOperationId);
}
