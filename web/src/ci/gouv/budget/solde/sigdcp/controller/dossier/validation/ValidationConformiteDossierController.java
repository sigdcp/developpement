package ci.gouv.budget.solde.sigdcp.controller.dossier.validation;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import ci.gouv.budget.solde.sigdcp.controller.dossier.DossierListeController;

@Named @ViewScoped
public class ValidationConformiteDossierController extends DossierListeController implements Serializable {

	private static final long serialVersionUID = -2412073347414420827L;

	/*
	 * Services
	 */

	
	/*
	 * Dto
	 */
		
	@Override
	protected void initialisation() {
		super.initialisation();
		//title = "Ecran de Validation de la conformité des dossiers";
		internalCode = "FS_REC_02_Ecran_01";
		defaultSubmitCommand.setValue(text("bouton.enregistrer"));
		
		
	}
	
	@Override
	protected String detailsOutcome() {
		return "demandeddDialog";
	}

	
	
	
	
}
