package ci.gouv.budget.solde.sigdcp.controller.reference;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import ci.gouv.budget.solde.sigdcp.model.identification.Categorie;
import ci.gouv.budget.solde.sigdcp.model.identification.Position;
import ci.gouv.budget.solde.sigdcp.model.identification.SituationMatrimoniale;

@Named @ViewScoped
public class SituationMatrimonialeReferenceController extends AbstractDynaEnumReferenceController<SituationMatrimoniale> implements Serializable {

	private static final long serialVersionUID = -1061481987684469949L;
	
	@Override
	protected String nomEntite() {
		return "Situation matrimoniale";
	}

	@Override
	protected Class<SituationMatrimoniale> clazz() {
		return SituationMatrimoniale.class;
	}

}
