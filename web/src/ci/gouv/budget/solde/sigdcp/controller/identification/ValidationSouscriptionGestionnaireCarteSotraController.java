package ci.gouv.budget.solde.sigdcp.controller.identification;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import lombok.Getter;
import ci.gouv.budget.solde.sigdcp.controller.ValidationDto;

@Named @ViewScoped
public class ValidationSouscriptionGestionnaireCarteSotraController extends SouscriptionGestionnaireCarteSotraController implements Serializable {

	private static final long serialVersionUID = -2412073347414420827L;

	/*
	 * Services
	 */
	
	/*
	 * Dto
	 */
	@Getter
	private ValidationDto validationDto;
	
	@Override
	public void initialisation() {
		super.initialisation();
		title = "Validation des préinscriptions des candidats gestionnaire de carte sotra";
		internalCode = "FS_SOTRA_02_Ecran_02";
		defaultSubmitCommand.setValue(messageManager.getTextService().find("bouton.valider"));
		validationDto = new ValidationDto(text("souscriptiongcsvalidequestion"));
	}

	
	
}
