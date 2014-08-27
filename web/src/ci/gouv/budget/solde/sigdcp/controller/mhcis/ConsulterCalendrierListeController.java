package ci.gouv.budget.solde.sigdcp.controller.mhcis;

import java.io.Serializable;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import ci.gouv.budget.solde.sigdcp.controller.ui.AbstractEntityListUIController;
import ci.gouv.budget.solde.sigdcp.model.calendrier.CalendrierMission;
import ci.gouv.budget.solde.sigdcp.service.calendrier.CalendrierMissionService;

@Named @ViewScoped
public class ConsulterCalendrierListeController extends AbstractEntityListUIController<CalendrierMission> implements Serializable {

	private static final long serialVersionUID = -2412073347414420827L;

	/*
	 * Services
	 */
	@Inject private CalendrierMissionService calendrierMissionService;
	
	/*
	 * Dto
	 */
	

	@Override
	public void initialisation() {
		super.initialisation();
		title = "Ecran de consultation de calendrier des missions";
		internalCode = "FS_MHCI_03_Ecran_04";
		System.out.println(nextViewOutcome);
	}

	@Override
	protected List<CalendrierMission> load() {
		return calendrierMissionService.findAll();
	}

	@Override
	public String href(CalendrierMission calendrierMission) {
		
		return navigationHelper.addQueryParameters(nextViewOutcome, new Object[]{webConstantResources.getRequestParamCalendrierMission(),calendrierMission.getId()});
	}
	
	
	
}
