package ci.gouv.budget.solde.sigdcp.controller.dossier;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import ci.gouv.budget.solde.sigdcp.controller.ui.form.AbstractEntityFormUIController;
import ci.gouv.budget.solde.sigdcp.model.dossier.PieceJustificative;
import ci.gouv.budget.solde.sigdcp.service.resources.CRUDType;

@Named @ViewScoped
public class ImprimerPieceJustificativeController extends AbstractEntityFormUIController<PieceJustificative> implements Serializable {

	private static final long serialVersionUID = -8067038151558697675L;

	@Override
	protected void initialisation() {
		crudType = CRUDType.READ;
		super.initialisation();
		title="Impression - "+entity.getModel();
	}
		
	@Override
	public CRUDType getCrudType() {
		return CRUDType.READ;
	}
	
}
