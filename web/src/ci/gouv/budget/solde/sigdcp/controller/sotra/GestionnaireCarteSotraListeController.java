package ci.gouv.budget.solde.sigdcp.controller.sotra;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import ci.gouv.budget.solde.sigdcp.controller.identification.SouscriptionGestionnaireCarteSotraListeController;
import ci.gouv.budget.solde.sigdcp.model.identification.souscription.SouscriptionGestionnaireCarteSotra;

@Named @ViewScoped
public class GestionnaireCarteSotraListeController extends SouscriptionGestionnaireCarteSotraListeController implements Serializable {

	private static final long serialVersionUID = -2412073347414420827L;

	/*
	 * Services
	 */
	
	
	/*
	 * Dto
	 */
	

	@Override
	public void initialisation() {
		super.initialisation();
		title = "Préinscription sur une liste de base de carte sotra";
		internalCode = "FS_SOTRA_03_Ecran_01";
		//defaultSubmitCommand.setValue(messageManager.getTextService().find("bouton.enregistrer"));
	}

	@Override
	public String href(SouscriptionGestionnaireCarteSotra souscriptionGestionnaireCarteSotra) {
		return navigationHelper.addQueryParameters(nextViewOutcome, new Object[]{webConstantResources.getRequestParamSouscription(), souscriptionGestionnaireCarteSotra.getCode()});
	}
	
	@Override
	public String getRecordIdentifierFieldName() {
		return "code";
	}
	
}
