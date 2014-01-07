package ci.gouv.budget.solde.sigdcp.service.dossier;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ci.gouv.budget.solde.sigdcp.model.dossier.DossierMission;
import ci.gouv.budget.solde.sigdcp.model.indemnite.GroupeMission;
import ci.gouv.budget.solde.sigdcp.model.indemnite.TypeClasseVoyage;

@Getter @Setter
@AllArgsConstructor
public class DossierMissionDTO implements Serializable {

	private static final long serialVersionUID = -3760863522940325593L;

	private DossierMission dossier;
	private GroupeMission groupe;
	private TypeClasseVoyage classeVoyage;
	
	
}
