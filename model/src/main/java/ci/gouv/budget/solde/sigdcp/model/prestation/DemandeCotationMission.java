/**
*    Syst�me Int�gr� de Gestion des D�penses Communes de Personnel - SIGDCP
*
*    Mod�le M�tier
*
**/


package ci.gouv.budget.solde.sigdcp.model.prestation;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import lombok.Getter;
import lombok.Setter;
import ci.gouv.budget.solde.sigdcp.model.calendrier.MissionExecutee;
import ci.gouv.budget.solde.sigdcp.model.traitement.Traitable;
import ci.gouv.budget.solde.sigdcp.model.traitement.TraitementDemandeCotationMission;

@Getter @Setter 
@Entity
//@Table(name="DEMCOTM")
public class DemandeCotationMission  extends DemandeCotation  implements Serializable{

	private static final long serialVersionUID = 6118425110229116743L;
	
	@ManyToOne
	private MissionExecutee mission;
	
	@Transient private Collection<Prestataire> prestataires=new ArrayList<>() ;
			
	@Embedded
	private Traitable<TraitementDemandeCotationMission> traitable = new Traitable<>();
	
	public DemandeCotationMission() {}

	public DemandeCotationMission(MissionExecutee mission) {
		super();
		this.mission = mission;
	}

	public DemandeCotationMission(MissionExecutee mission,	Collection<Prestataire> prestataires) {
		super();
		this.mission = mission;
		this.prestataires = prestataires;
	}

	public Long getMissionId(){
		return mission.getId();
	}
	
	
} 