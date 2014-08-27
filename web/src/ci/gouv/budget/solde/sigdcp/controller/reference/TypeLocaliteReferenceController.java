package ci.gouv.budget.solde.sigdcp.controller.reference;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import ci.gouv.budget.solde.sigdcp.model.geographie.TypeLocalite;
import ci.gouv.budget.solde.sigdcp.model.identification.Categorie;
import ci.gouv.budget.solde.sigdcp.model.identification.Position;

@Named @ViewScoped
public class TypeLocaliteReferenceController extends AbstractDynaEnumReferenceController<TypeLocalite> implements Serializable {

	private static final long serialVersionUID = -1061481987684469949L;
	
	@Override
	protected String nomEntite() {
		return "Type de localité";
	}

	@Override
	protected Class<TypeLocalite> clazz() {
		return TypeLocalite.class;
	}

}
