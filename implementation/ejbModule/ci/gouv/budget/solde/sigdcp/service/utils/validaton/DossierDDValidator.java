package ci.gouv.budget.solde.sigdcp.service.utils.validaton;

import java.io.Serializable;

import javax.validation.constraints.AssertTrue;

import ci.gouv.budget.solde.sigdcp.model.dossier.DossierDD;

public class DossierDDValidator extends AbstractValidator<DossierDD> implements Serializable {

	private static final long serialVersionUID = -261860698364195138L;
	
	@AssertTrue(message="Le dossier doit appartenir a un bénéficiaire")
	private boolean hasBenficiaire(){
		return object.getBeneficiaire()!=null;
	}
	
	
}
