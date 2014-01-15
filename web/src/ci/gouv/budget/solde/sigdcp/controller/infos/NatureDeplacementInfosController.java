package ci.gouv.budget.solde.sigdcp.controller.infos;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import ci.gouv.budget.solde.sigdcp.controller.ui.form.AbstractEntityFormUIController;
import ci.gouv.budget.solde.sigdcp.model.dossier.NatureDeplacement;

@Named @RequestScoped
public class NatureDeplacementInfosController extends AbstractEntityFormUIController<NatureDeplacement> implements Serializable {

	private static final long serialVersionUID = 7404320093212948431L;

	@Override
	protected void initialisation() {
		super.initialisation();
		title = entity.getLibelle();
	}
	
	
}
