package ci.gouv.budget.solde.sigdcp.controller.mhcis;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ci.gouv.budget.solde.sigdcp.model.calendrier.Mission;

@Getter @Setter @AllArgsConstructor
public class MissionDto implements Serializable {

	private static final long serialVersionUID = -5745228665475711694L;

	private Mission mission;
	
	private String participants;
	
	private Float montant;
	
	
	
}
