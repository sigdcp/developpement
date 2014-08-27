package ci.gouv.budget.solde.sigdcp.controller.remboursement;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import ci.gouv.budget.solde.sigdcp.model.Code;

@Named @ViewScoped
public class ReceptionnerProjetDecisionController extends AbstractProjetDecisionListePersonnelController implements Serializable {

	private static final long serialVersionUID = -2412073347414420827L;
	
	@Override
	protected void initialisation() {
		super.initialisation();
		listTitle="Liste des projets de décision";
		selectLabel="bouton.selectionner";
	}
	
	@Override
	protected String natureOperationCode() {
		return Code.NATURE_OPERATION_RECEPTION_PROJDEC;
	}
				
}
