package ci.gouv.budget.solde.sigdcp.controller.reference;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import ci.gouv.budget.solde.sigdcp.model.identification.Categorie;

@Named @ViewScoped
public class CategorieReferenceController extends AbstractDynaEnumReferenceController<Categorie> implements Serializable {

	private static final long serialVersionUID = -1061481987684469949L;
	
	@Override
	protected String nomEntite() {
		return "Catégorie";
	}

	@Override
	protected Class<Categorie> clazz() {
		return Categorie.class;
	}

}
