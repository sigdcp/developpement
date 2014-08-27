package ci.gouv.budget.solde.sigdcp.controller.reference;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import ci.gouv.budget.solde.sigdcp.model.identification.Categorie;
import ci.gouv.budget.solde.sigdcp.model.identification.Position;

@Named @ViewScoped
public class PositionReferenceController extends AbstractDynaEnumReferenceController<Position> implements Serializable {

	private static final long serialVersionUID = -1061481987684469949L;
	
	@Override
	protected String nomEntite() {
		return "Position";
	}

	@Override
	protected Class<Position> clazz() {
		return Position.class;
	}

}
