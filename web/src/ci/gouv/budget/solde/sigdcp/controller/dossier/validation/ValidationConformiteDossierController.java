package ci.gouv.budget.solde.sigdcp.controller.dossier.validation;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import ci.gouv.budget.solde.sigdcp.model.Code;

@Named @ViewScoped
public class ValidationConformiteDossierController extends AbstractValidationDossierController implements Serializable {

	private static final long serialVersionUID = -2412073347414420827L;
	
	@Override
	protected String natureOperationCode() {
		return Code.NATURE_OPERATION_CONFORMITE;
	}

	@Override
	protected String[] defaultNatureDeplacmentCodeListe() {
		return touteLesDepenses();
	}
	
	@Override
	protected Boolean canShowAllNatureDeplacment() {
		return true;
	}
		
}
