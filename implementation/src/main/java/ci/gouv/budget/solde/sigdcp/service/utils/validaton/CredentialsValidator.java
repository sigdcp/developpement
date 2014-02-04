package ci.gouv.budget.solde.sigdcp.service.utils.validaton;

import java.io.Serializable;

import javax.inject.Inject;

import lombok.Getter;
import ci.gouv.budget.solde.sigdcp.model.identification.Credentials;
import ci.gouv.budget.solde.sigdcp.service.utils.ServiceValidationUtils;

@Getter
public class CredentialsValidator extends AbstractValidator<Credentials> implements Serializable {

	private static final long serialVersionUID = -261860698364195138L;
	
	@Inject private ServiceValidationUtils validationUtils;	
	
}
