package ci.gouv.budget.solde.sigdcp.controller.reference;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import ci.gouv.budget.solde.sigdcp.model.dossier.CauseDeces;

@Named @ViewScoped
public class CausedecesReferenceController extends AbstractDynaEnumReferenceController<CauseDeces> implements Serializable {

	private static final long serialVersionUID = 1L;

	@Override
	protected String nomEntite() {
		return "Cause de décès";
	}

	@Override
	protected Class<CauseDeces> clazz() {
		return CauseDeces.class;
	}

}
