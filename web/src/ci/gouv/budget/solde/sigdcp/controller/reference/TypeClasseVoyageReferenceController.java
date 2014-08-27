package ci.gouv.budget.solde.sigdcp.controller.reference;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import ci.gouv.budget.solde.sigdcp.model.identification.Categorie;
import ci.gouv.budget.solde.sigdcp.model.identification.Position;
import ci.gouv.budget.solde.sigdcp.model.identification.SituationMatrimoniale;
import ci.gouv.budget.solde.sigdcp.model.indemnite.TypeClasseVoyage;
import ci.gouv.budget.solde.sigdcp.model.traitement.Statut;

@Named @ViewScoped
public class TypeClasseVoyageReferenceController extends AbstractDynaEnumReferenceController<TypeClasseVoyage> implements Serializable {

	private static final long serialVersionUID = -1061481987684469949L;
	
	@Override
	protected String nomEntite() {
		return "Type de classe de voyage";
	}

	@Override
	protected Class<TypeClasseVoyage> clazz() {
		return TypeClasseVoyage.class;
	}

}
