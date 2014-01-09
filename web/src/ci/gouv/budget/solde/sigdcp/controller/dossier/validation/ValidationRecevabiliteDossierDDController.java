package ci.gouv.budget.solde.sigdcp.controller.dossier.validation;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import lombok.Getter;
import lombok.Setter;
import ci.gouv.budget.solde.sigdcp.controller.dossier.EnregistrerDemandeDDController;

@Named @ViewScoped
public class ValidationRecevabiliteDossierDDController extends EnregistrerDemandeDDController implements Serializable {

	private static final long serialVersionUID = -2412073347414420827L;

	/*
	 * Services
	 */
	
	/*
	 * Dto
	 */
	@Getter @Setter private Boolean accepter;
		
	@Override
	protected void initialisation() {
		super.initialisation();
		title = "Ecran de Validation de la recevabilité d'une demande : "+entity.getDeplacement().getNature().getLibelle();
		internalCode = "FS_DEM_01_Ecran_02";
		defaultSubmitCommand.setValue(text("bouton.enregistrer"));
	}

	@Override
	protected void onDefaultSubmitAction() throws Exception {
		if(accepter)
			getDossierService().accepterRecevabilite(entity);
		else
			getDossierService().rejeterConformite(entity, null);
	}
	
	
	
}
