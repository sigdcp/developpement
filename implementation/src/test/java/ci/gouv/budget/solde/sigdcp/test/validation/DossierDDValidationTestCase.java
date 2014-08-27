package ci.gouv.budget.solde.sigdcp.test.validation;

import junit.framework.Test;
import junit.framework.TestSuite;
import ci.gouv.budget.solde.sigdcp.model.dossier.DossierDD;
import ci.gouv.budget.solde.sigdcp.service.utils.validaton.AbstractValidator;
import ci.gouv.budget.solde.sigdcp.service.utils.validaton.DossierDDValidator;

public class DossierDDValidationTestCase extends AbstractValidationTestCase<DossierDD> {

	public DossierDDValidationTestCase(String testName) {
		super(testName);
	}


	public static Test suite() {
		return new TestSuite(DossierDDValidationTestCase.class);
	}
	
	@Override
	protected DossierDD createObject() {
		DossierDD dossier = new DossierDD();
		return dossier;
	}

	@Override
	protected AbstractValidator<DossierDD> createValidator() {
		return new DossierDDValidator();
	}

}