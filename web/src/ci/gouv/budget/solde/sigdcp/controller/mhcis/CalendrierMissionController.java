package ci.gouv.budget.solde.sigdcp.controller.mhcis;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import lombok.Getter;
import lombok.Setter;
import ci.gouv.budget.solde.sigdcp.controller.ui.AbstractEntityListUIController;
import ci.gouv.budget.solde.sigdcp.model.calendrier.CalendrierMission;
import ci.gouv.budget.solde.sigdcp.model.calendrier.Exercice;
import ci.gouv.budget.solde.sigdcp.service.calendrier.CalendrierMissionService;
import ci.gouv.budget.solde.sigdcp.service.calendrier.ExerciceService;

@Named @ViewScoped
public class CalendrierMissionController extends AbstractEntityListUIController<CalendrierMission> implements Serializable {

	private static final long serialVersionUID = -2412073347414420827L;

	/*
	 * Services
	*/
	@Inject private CalendrierMissionService calendrierMissionService;
	@Inject private ExerciceService exerciceService;
	
	
	/*
	 * Dto
	 */
	@Getter private Exercice exerciceCourant;
	@Getter @Setter private Exercice exercice;

	@Override
	public void initialisation() {
		super.initialisation();
		title = "Ecran de consultation de calendrier des missions";
		internalCode = "FS_MHCI_03_Ecran_04";
		
		enableSearch();
	}


	@Override
	protected List<CalendrierMission> load() {
		exerciceCourant = exerciceService.findCurrent();
		return new LinkedList<>(calendrierMissionService.findByExercice(exerciceCourant));
	}
	

	@Override
	public String href(CalendrierMission calendrierMission) {
		
		return navigationHelper.addQueryParameters(nextViewOutcome, new Object[]{webConstantResources.getRequestParamCalendrierMission(),calendrierMission.getId()});
	}
	
	@Override
	protected void onSearchCommandAction() {
		//System.out.println(exercice);
		if(exercice==null)
		list = load();
	else
		list = new LinkedList<>(calendrierMissionService.findByExercice(exercice));
}

	
	
}
