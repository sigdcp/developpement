package ci.gouv.budget.solde.sigdcp.controller.reference;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import ci.gouv.budget.solde.sigdcp.model.indemnite.RegleCalcul;

@Named @ViewScoped
public class RegleCalculReferenceController extends AbstractDynaEnumReferenceController<RegleCalcul> implements Serializable {

	private static final long serialVersionUID = -1061481987684469949L;
	
	@Override
	protected String nomEntite() {
		return "Règle de calcul des Indemnités";
	}

	@Override
	protected Class<RegleCalcul> clazz() {
		return RegleCalcul.class;
	}

}
