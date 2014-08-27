package ci.gouv.budget.solde.sigdcp.controller.reference;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import ci.gouv.budget.solde.sigdcp.model.identification.Grade;

@Named @ViewScoped
public class GradeReferenceController extends AbstractDynaEnumReferenceController<Grade> implements Serializable {

	private static final long serialVersionUID = -1061481987684469949L;
	
	@Override
	protected String nomEntite() {
		return "Grade";
	}

	@Override
	protected Class<Grade> clazz() {
		return Grade.class;
	}

}
