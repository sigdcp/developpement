package ci.gouv.budget.solde.sigdcp.controller.reference;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import ci.gouv.budget.solde.sigdcp.model.dossier.TypeDepense;

@Named @ViewScoped
public class TypeDepenseReferenceController extends AbstractDynaEnumReferenceController<TypeDepense> implements Serializable {

	private static final long serialVersionUID = -1061481987684469949L;
	
	@Override
	protected String nomEntite() {
		return "Type de dépense";
	}

	@Override
	protected Class<TypeDepense> clazz() {
		return TypeDepense.class;
	}

}
