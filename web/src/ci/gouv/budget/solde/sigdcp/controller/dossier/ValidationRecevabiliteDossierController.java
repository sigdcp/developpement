package ci.gouv.budget.solde.sigdcp.controller.dossier;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

@Named @ViewScoped
public class ValidationRecevabiliteDossierController extends EnregistrerDemandeDDController implements Serializable {

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
		title = "Ecran de Validation d'une demande : "+entity.getDeplacement().getNature().getLibelle();
		internalCode = "FS_DEM_01_Ecran_02";
		defaultSubmitCommand.setValue(text("bouton.enregistrer"));
		
	}

	
	
	
	
}
