package ci.gouv.budget.solde.sigdcp.controller.dossier;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import lombok.Getter;
import ci.gouv.budget.solde.sigdcp.controller.ui.form.AbstractEntityFormUIController;
import ci.gouv.budget.solde.sigdcp.model.dossier.BordereauTransmission;

@Named @ViewScoped
public class BordereauTransmissionController extends AbstractEntityFormUIController<BordereauTransmission> implements Serializable {

	private static final long serialVersionUID = -1893812779864962658L;

	
	@Getter @Inject
	private BordereauSelectionPieceController selectionPieceController;
	
	@Override
	protected void initialisation() {
		super.initialisation();
		title="Réaliser un bordereau de bulletin de liquidation";
		
	}
	
}
