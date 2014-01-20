package ci.gouv.budget.solde.sigdcp.controller.identification;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import lombok.Getter;
import ci.gouv.budget.solde.sigdcp.controller.ValidationDto;
import ci.gouv.budget.solde.sigdcp.controller.sotra.InscriptionListeGestionnaireCarteSotraForm;

@Named @ViewScoped
public class ValidationInscriptionListeGestionnaireCarteSotraController extends InscriptionListeGestionnaireCarteSotraForm implements Serializable {

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
		title = "Formulaire de validation de l'inscription d'un agent l'état sur la liste de base";
		internalCode = "FS_SOTRA_03_Ecran_02_01";
		defaultSubmitCommand.setValue(messageManager.getTextService().find("bouton.valider"));
		validationDto = new ValidationDto(text("inscriptionlistegcsvalidequestion"));
	}

	
	
}
