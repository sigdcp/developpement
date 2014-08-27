package ci.gouv.budget.solde.sigdcp.controller.reference;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import ci.gouv.budget.solde.sigdcp.model.identification.Categorie;
import ci.gouv.budget.solde.sigdcp.model.identification.Position;
import ci.gouv.budget.solde.sigdcp.model.identification.SituationMatrimoniale;
import ci.gouv.budget.solde.sigdcp.model.traitement.Statut;

@Named @ViewScoped
public class StatutReferenceController extends AbstractDynaEnumReferenceController<Statut> implements Serializable {

	private static final long serialVersionUID = -1061481987684469949L;
	
	@Override
	protected String nomEntite() {
		return "Statut";
	}

	@Override
	protected Class<Statut> clazz() {
		return Statut.class;
	}

}
