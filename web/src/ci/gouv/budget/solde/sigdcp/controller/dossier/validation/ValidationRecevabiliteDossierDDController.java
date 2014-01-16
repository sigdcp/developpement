package ci.gouv.budget.solde.sigdcp.controller.dossier.validation;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.validation.Valid;

import lombok.Getter;
import ci.gouv.budget.solde.sigdcp.controller.ValidationDto;
import ci.gouv.budget.solde.sigdcp.controller.dossier.EnregistrerDemandeDDController;
import ci.gouv.budget.solde.sigdcp.model.Code;

@Named @ViewScoped
public class ValidationRecevabiliteDossierDDController extends EnregistrerDemandeDDController implements Serializable {

	private static final long serialVersionUID = -2412073347414420827L;

	/*
	 * Services
	 */
	
	/*
	 * Dto
	 */
	@Valid
	@Getter private ValidationDto validationDto;
		
	@Override
	protected void initialisation() {
		super.initialisation();
		title = "Ecran de Validation de la recevabilité d'une demande : "+entity.getDeplacement().getNature().getLibelle();
		internalCode = "FS_DEM_01_Ecran_02";
		validationDto = new ValidationDto(text("souscriptiongcsvalidequestion"));
		
		defaultSubmitCommand.setValue(text("bouton.enregistrer"));
		defaultSubmitCommand.setRendered(Boolean.TRUE);
		//defaultSubmitCommand.addExtraValidatorFor(this);
		
		closeCommand.setSuccessOutcome(navigationManager.url("dossierliste",new Object[]{
				webConstantResources.getRequestParamStatut(),Code.STATUT_SOUMIS,webConstantResources.getRequestParamNextViewOutcome(),"validationrecevabilite"
		}));
		
		
	}

	@Override
	protected void onDefaultSubmitAction() throws Exception {
		if(Boolean.TRUE.equals(validationDto.getAccepter()))
			getDossierService().accepterRecevabilite(entity);
		else
			getDossierService().rejeterConformite(entity, null);
	}
	
	
	
}
