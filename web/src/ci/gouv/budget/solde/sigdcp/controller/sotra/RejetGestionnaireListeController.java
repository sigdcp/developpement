package ci.gouv.budget.solde.sigdcp.controller.sotra;

import java.io.Serializable;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import ci.gouv.budget.solde.sigdcp.controller.ui.AbstractEntityListUIController;
import ci.gouv.budget.solde.sigdcp.model.identification.DelegueSotra;

@Named @ViewScoped
public class RejetGestionnaireListeController extends AbstractEntityListUIController<DelegueSotra> implements Serializable {

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
		title = "Liste des inscrits rejetés";
		internalCode = "FS_SOTRA_02_Ecran_04";
		enableSearch();
		
		defaultSubmitCommand.setValue(messageManager.getTextService().find("bouton.editer"));
	}

	@Override
	protected List<DelegueSotra> load() {
		return null;//calendrierMissionService.findAll();
	}

	@Override
	public String href(DelegueSotra gestionnaire) {
		
		return null;//navigationManager.addQueryParameters(nextViewOutcome, new Object[]{webConstantResources.getRequestParamCalendrierMission(),calendrierMission.getId()});
	}
	
	
	
}
