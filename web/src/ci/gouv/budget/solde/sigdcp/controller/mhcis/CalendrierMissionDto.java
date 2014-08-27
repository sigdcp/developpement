package ci.gouv.budget.solde.sigdcp.controller.mhcis;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ci.gouv.budget.solde.sigdcp.model.calendrier.CalendrierMission;

@Getter @Setter @AllArgsConstructor
public class CalendrierMissionDto implements Serializable {

	private static final long serialVersionUID = -5745228665475711694L;

	private CalendrierMission calendrierMission;
	
	private String montantExecute;
	
	private Float ecart;
	
	
	
}
