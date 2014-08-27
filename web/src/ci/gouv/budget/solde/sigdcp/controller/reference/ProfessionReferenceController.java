package ci.gouv.budget.solde.sigdcp.controller.reference;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import ci.gouv.budget.solde.sigdcp.model.identification.Profession;

@Named @ViewScoped
public class ProfessionReferenceController extends AbstractDynaEnumReferenceController<Profession> implements Serializable {

	private static final long serialVersionUID = -1061481987684469949L;
	
	@Override
	protected String nomEntite() {
		return "Profession";
	}
	
	@Override
	protected Class<Profession> clazz() {
		// TODO Auto-generated method stub
		return Profession.class;
	}
	
	

}
