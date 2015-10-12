package ci.gouv.budget.solde.sigdcp.controller.reference;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import ci.gouv.budget.solde.sigdcp.model.indemnite.Cercueil;

@Named @ViewScoped
public class CercueilReferenceController extends AbstractDynaEnumReferenceController<Cercueil> implements Serializable {

	private static final long serialVersionUID = -1061481987684469949L;
	
	@Override
	protected String nomEntite() {
		return "Cercueil";
	}

	@Override
	protected Class<Cercueil> clazz() {
		return Cercueil.class;
	}

}
