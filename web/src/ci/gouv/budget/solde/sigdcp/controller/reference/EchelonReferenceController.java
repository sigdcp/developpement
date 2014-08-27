package ci.gouv.budget.solde.sigdcp.controller.reference;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import ci.gouv.budget.solde.sigdcp.model.identification.Echelon;

@Named @ViewScoped
public class EchelonReferenceController extends AbstractDynaEnumReferenceController<Echelon> implements Serializable {

	private static final long serialVersionUID = -1061481987684469949L;
	
	@Override
	protected String nomEntite() {
		return "Echélon";
	}

	@Override
	protected Class<Echelon> clazz() {
		return Echelon.class;
	}

}
