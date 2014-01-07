/**
*    Syst�me Int�gr� de Gestion des D�penses Communes de Personnel - SIGDCP
*
*    Mod�le M�tier
*
**/


package ci.gouv.budget.solde.sigdcp.model.dossier;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;

import ci.gouv.budget.solde.sigdcp.model.calendrier.Mission;
import ci.gouv.budget.solde.sigdcp.model.identification.AgentEtat;
import ci.gouv.budget.solde.sigdcp.model.identification.Grade;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter 
@Entity
public class DossierMission  extends Dossier   implements Serializable{

	private static final long serialVersionUID = 1L;

	public DossierMission() {
		super();
	}

	public DossierMission(String numero, Date dateDepot, String numeroCourrier, Mission mission, Grade grade,AgentEtat participant) {
		super(numero, dateDepot, numeroCourrier, null, mission, grade,participant);
	}
	
	

}