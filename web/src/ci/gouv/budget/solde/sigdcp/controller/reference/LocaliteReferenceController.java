package ci.gouv.budget.solde.sigdcp.controller.reference;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import ci.gouv.budget.solde.sigdcp.model.geographie.Localite;


@Named @ViewScoped
public class LocaliteReferenceController extends AbstractDynaEnumReferenceController<Localite> implements Serializable {

	private static final long serialVersionUID = -1061481987684469949L;
	
	@Override
	protected String nomEntite() {
		return "Localité";
	}

	@Override
	protected Class<Localite> clazz() {
		return Localite.class;
	}
	
	

}
