package ci.gouv.budget.solde.sigdcp.controller.reference;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import ci.gouv.budget.solde.sigdcp.model.dossier.CategorieDeplacement;

@Named @ViewScoped
public class CategorieDeplacementReferenceController extends AbstractDynaEnumReferenceController<CategorieDeplacement> implements Serializable {

	private static final long serialVersionUID = -1061481987684469949L;
	
	@Override
	protected void initialisation() {		
		super.initialisation();
		etatBoutonAjouter = false;
	}
	
	@Override
	protected String nomEntite() {
		return "Catégorie de déplacement";
	}

	@Override
	protected Class<CategorieDeplacement> clazz() {
		return CategorieDeplacement.class;
	}

}
