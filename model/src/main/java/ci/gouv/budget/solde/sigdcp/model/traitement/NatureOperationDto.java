package ci.gouv.budget.solde.sigdcp.model.traitement;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;

import lombok.Getter;
import lombok.Setter;
import ci.gouv.budget.solde.sigdcp.model.AbstractModel;
import ci.gouv.budget.solde.sigdcp.model.dossier.ValidationType;

@Getter @Setter
public class NatureOperationDto extends AbstractModel<Long> implements Serializable {

	private static final long serialVersionUID = -5891558992322908585L;

	private NatureOperation natureOperation;
	
	private Long configCount=0L;
	private Collection<ValidationType> validationTypes = new HashSet<>();
	private OperationValidationConfig operationValidationConfigAccepte,operationValidationConfigAcceptePrecedent;

	public NatureOperationDto(NatureOperation natureOperation) {
		super();
		this.natureOperation = natureOperation;
	}
	
	
	
}
