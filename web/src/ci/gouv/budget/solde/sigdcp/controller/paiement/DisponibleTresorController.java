package ci.gouv.budget.solde.sigdcp.controller.paiement;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import lombok.Getter;
import lombok.Setter;
import ci.gouv.budget.solde.sigdcp.controller.ui.form.AbstractEntityFormUIController;
import ci.gouv.budget.solde.sigdcp.model.Code;
import ci.gouv.budget.solde.sigdcp.model.dossier.CategorieDeplacement;
import ci.gouv.budget.solde.sigdcp.service.dossier.CategorieDeplacementService;

@Named @ViewScoped
public class DisponibleTresorController extends AbstractEntityFormUIController<CategorieDeplacement> implements Serializable {

	private static final long serialVersionUID = 2335993962631918255L;
	
	@Inject
	private CategorieDeplacementService categorieDeplacementService;
	
	@Getter @Setter private BigDecimal montantEnAjout;
	
	@Override
	protected void initialisation() {
		super.initialisation();
		title = "Mettre à jour le disponible";
		entity = genericService.findByClass(CategorieDeplacement.class, Code.CATEGORIE_DEPLACEMENT_DEFINITIF);
		defaultSubmitCommand.setValue(text("bouton.valider"));
	}
	
	@Override
	protected void onDefaultSubmitAction() throws Exception {
		categorieDeplacementService.mettreAJourDisponibleTresor(entity,montantEnAjout);
	}

}
