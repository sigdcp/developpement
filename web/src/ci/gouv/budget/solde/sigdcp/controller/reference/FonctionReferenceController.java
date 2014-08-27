package ci.gouv.budget.solde.sigdcp.controller.reference;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import ci.gouv.budget.solde.sigdcp.model.identification.Categorie;
import ci.gouv.budget.solde.sigdcp.model.identification.Fonction;

@Named @ViewScoped
public class FonctionReferenceController extends AbstractDynaEnumReferenceController<Fonction> implements Serializable {

	private static final long serialVersionUID = -1061481987684469949L;
	
	@Override
	protected String nomEntite() {
		return "Fonction";
	}

	@Override
	protected Class<Fonction> clazz() {
		return Fonction.class;
	}

}
