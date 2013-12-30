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
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;
import ci.gouv.budget.solde.sigdcp.model.dossier.Deplacement;
import ci.gouv.budget.solde.sigdcp.model.dossier.DossierMHCI;
import ci.gouv.budget.solde.sigdcp.model.indemnite.MontantIndemniteMission;

@Getter @Setter 
@Entity
public class Mission  extends Deplacement   implements Serializable{

	private static final long serialVersionUID = 1L;

	private String designation;
	
	private Integer mois;
	
	private Integer dureeJour;
	
	private String objetifs;
	
	private String resultatsAttendu; 
	
	private Boolean prevue;
	
	@ManyToOne
	private CalendrierMission calendrierMission;
	
	@ManyToOne
	private DossierMHCI dossierDuResponsable;
	
	@Embedded
	private MontantIndemniteMission indemnite;
}