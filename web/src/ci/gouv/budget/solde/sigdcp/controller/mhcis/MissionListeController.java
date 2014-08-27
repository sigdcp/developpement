package ci.gouv.budget.solde.sigdcp.controller.mhcis;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import lombok.Getter;
import lombok.Setter;
import ci.gouv.budget.solde.sigdcp.controller.ui.AbstractEntityListUIController;
import ci.gouv.budget.solde.sigdcp.model.calendrier.CalendrierMission;
import ci.gouv.budget.solde.sigdcp.model.calendrier.Exercice;
import ci.gouv.budget.solde.sigdcp.service.calendrier.CalendrierMissionService;
import ci.gouv.budget.solde.sigdcp.service.calendrier.ExerciceService;

@Deprecated
public class MissionListeController extends AbstractEntityListUIController<CalendrierMission> implements Serializable {

	private static final long serialVersionUID = -2412073347414420827L;

	/*
	 * Services
	 */
	
	@Inject private CalendrierMissionService calendrierMissionService;
	@Inject private ExerciceService exerciceService;
	
	/*
	 * Dto
	 */
	
	
	/*
	 * Paramètres url
	 */

	@Getter @Setter private Exercice exerciceCourant;
	
	@Override
	public void initialisation() {
		super.initialisation();	
		System.out.println("MissionListeController.initialisation()"+nextViewOutcome);
		title = "Ecran de suivi de l'exécution des missions";
		internalCode = "FS_MHCI_04_Ecran_01";
	}
	

	@Override
	protected List<CalendrierMission> load() {
		exerciceCourant = exerciceService.findCurrent();
		return new LinkedList<>(calendrierMissionService.findByExercice(exerciceCourant));
	}

	@Override
	public String href(CalendrierMission entity) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
