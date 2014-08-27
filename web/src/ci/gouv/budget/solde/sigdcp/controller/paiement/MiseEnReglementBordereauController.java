package ci.gouv.budget.solde.sigdcp.controller.paiement;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import ci.gouv.budget.solde.sigdcp.model.Code;

@Named @ViewScoped
public class MiseEnReglementBordereauController extends AbstractValidationBordereauTransmissionController implements Serializable {

	private static final long serialVersionUID = 2335993962631918255L;
	
	@Override
	protected void initialisation() {
		super.initialisation();
		showTotalDepense=Boolean.TRUE;
	}
	
	@Override
	protected String natureOperationCode() {
		return Code.NATURE_OPERATION_MISE_EN_REGLEMENT;
	}

}
