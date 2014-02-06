package ci.gouv.budget.solde.sigdcp.controller.identification;

import java.io.Serializable;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import ci.gouv.budget.solde.sigdcp.controller.ui.AbstractEntityListUIController;
import ci.gouv.budget.solde.sigdcp.model.identification.souscription.SouscriptionGestionnaireCarteSotra;
import ci.gouv.budget.solde.sigdcp.service.identification.SouscriptionGestionnaireCarteSotraService;

@Named @ViewScoped
public class SouscriptionGestionnaireCarteSotraListeController extends AbstractEntityListUIController<SouscriptionGestionnaireCarteSotra> implements Serializable {

	private static final long serialVersionUID = -2412073347414420827L;

	/*
	 * Services
	 */
	@Inject private SouscriptionGestionnaireCarteSotraService souscriptionGestionnaireCarteSotraService;
	
	/*
	 * Dto
	 */
	

	@Override
	public void initialisation() {
		super.initialisation();
		title = "Validation des préinscriptions des candidats gestionnaire de carte sotra";
		internalCode = "FS_SOTRA_02_Ecran_02";
		defaultSubmitCommand.setValue(messageManager.getTextService().find("bouton.enregistrer"));
	}

	@Override
	protected List<SouscriptionGestionnaireCarteSotra> load() {
		return souscriptionGestionnaireCarteSotraService.findAll();
	}

	@Override
	public String href(SouscriptionGestionnaireCarteSotra souscriptionGestionnaireCarteSotra) {
		return navigationHelper.addQueryParameters(nextViewOutcome, new Object[]{
				webConstantResources.getRequestParamCrudType(),webConstantResources.getRequestParamCrudRead(),
				webConstantResources.getRequestParamSouscription(), souscriptionGestionnaireCarteSotra.getCode()});
	}
	
	@Override
	public String getRecordIdentifierFieldName() {
		return "code";
	}
	
}
