package ci.gouv.budget.solde.sigdcp.controller.mhcis;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import lombok.Getter;
import ci.gouv.budget.solde.sigdcp.controller.ui.form.AbstractEntityFormUIController;
import ci.gouv.budget.solde.sigdcp.model.calendrier.CalendrierMission;

//@Named @ViewScoped
public class ConsulterCalendrierMissionController extends AbstractEntityFormUIController<CalendrierMission> implements Serializable {

	private static final long serialVersionUID = 7125727696323594059L;

	//@Inject private CalendrierMissionService missionService;
	
	@Getter
	private List<MissionDto> missionDtos = new LinkedList<>();
	
	@Override
	protected void initialisation() {
		super.initialisation();
		title = "Ecran de consultation détaillée d'un calendrier de mission";
		internalCode = "FS_MHCI_03_Ecran_05";
		/*for(Mission mission : new LinkedList<>(missionService.findByCalendrier(entity))){
			String participants = "2A , 1H";
			missionDtos.add(new MissionDto(mission, participants,10000f));
		}*/
		closeCommand.setValue(text("retouraliste"));
		defaultSubmitCommand.setRendered(Boolean.FALSE);
	}
	
}
