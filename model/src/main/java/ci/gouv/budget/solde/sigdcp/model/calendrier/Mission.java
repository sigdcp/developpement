/**
*    Syst�me Int�gr� de Gestion des D�penses Communes de Personnel - SIGDCP
*
*    Mod�le M�tier
*
**/


package ci.gouv.budget.solde.sigdcp.model.calendrier;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;
import ci.gouv.budget.solde.sigdcp.model.dossier.Deplacement;
import ci.gouv.budget.solde.sigdcp.model.dossier.DossierMission;
import ci.gouv.budget.solde.sigdcp.model.dossier.NatureDeplacement;
import ci.gouv.budget.solde.sigdcp.model.geographie.Localite;
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
	private DossierMission dossierDuResponsable;
	
	@Embedded
	private MontantIndemniteMission indemnite;
	
	public Mission() {}

	public Mission(CalendrierMission calendrierMission,Date dateDepart, Date dateArrivee,NatureDeplacement nature,Localite depart, Localite arrivee,
			String designation, Integer mois, Integer dureeJour,String objetifs, String resultatsAttendu) {
		super(null, dateDepart, dateArrivee, null, nature, depart, arrivee);
		this.calendrierMission = calendrierMission;
		this.designation = designation;
		this.mois = mois;
		this.dureeJour = dureeJour;
		this.objetifs = objetifs;
		this.resultatsAttendu = resultatsAttendu;
	}

	
	
	
}