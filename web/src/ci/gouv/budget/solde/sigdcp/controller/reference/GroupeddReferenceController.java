package ci.gouv.budget.solde.sigdcp.controller.reference;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import ci.gouv.budget.solde.sigdcp.model.indemnite.GroupeDD;

@Named @ViewScoped
public class GroupeddReferenceController extends AbstractDynaEnumReferenceController<GroupeDD> implements Serializable {

	private static final long serialVersionUID = -1061481987684469949L;
	
	@Override
	protected String nomEntite() {
		return "Groupe de déplacement définitif";
	}

	@Override
	protected Class<GroupeDD> clazz() {
		return GroupeDD.class;
	}

}
