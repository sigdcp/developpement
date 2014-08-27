/**
*    Syst�me Int�gr� de Gestion des D�penses Communes de Personnel - SIGDCP
*
*    Mod�le M�tier
*
**/


package ci.gouv.budget.solde.sigdcp.model.calendrier;

import java.io.Serializable;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;
import ci.gouv.budget.solde.sigdcp.model.indemnite.FraisMission;
import ci.gouv.budget.solde.sigdcp.model.utils.validation.groups.Client;

@Getter @Setter 
@Entity
public class MissionProgrammee extends Mission implements Serializable{

	private static final long serialVersionUID = 1L;

	@NotNull(groups=Client.class)
	private Integer mois;
	
	@NotNull(groups=Client.class)
	private Integer dureeJour;
	
	@NotNull(groups=Client.class)
	private String objetifs;
	
	@NotNull(groups=Client.class)
	private String resultatsAttendu; 
	
	//@ManyToOne @NotNull(groups=Client.class) @JoinColumn
	//private CalendrierMission calendrierMission;
	
	@Embedded
	private FraisMission indemnite;
	
	public MissionProgrammee() {}

	public MissionProgrammee(String designation, Integer mois,
			Integer dureeJour, String objetifs, String resultatsAttendu,
			CalendrierMission calendrierMission,
			FraisMission indemnite) {
		super(designation);
		this.mois = mois;
		this.dureeJour = dureeJour;
		this.objetifs = objetifs;
		this.resultatsAttendu = resultatsAttendu;
		//this.calendrierMission = calendrierMission;
		this.indemnite = indemnite;
	}

	

	

	
	
	
}