/**
*    Syst�me Int�gr� de Gestion des D�penses Communes de Personnel - SIGDCP
*
*    Mod�le M�tier
*
**/


package ci.gouv.budget.solde.sigdcp.model.dossier;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Getter;
import lombok.Setter;
import ci.gouv.budget.solde.sigdcp.model.identification.AgentEtat;
import ci.gouv.budget.solde.sigdcp.model.identification.Fonction;
import ci.gouv.budget.solde.sigdcp.model.identification.Grade;
import ci.gouv.budget.solde.sigdcp.model.identification.Profession;
import ci.gouv.budget.solde.sigdcp.model.indemnite.FraisMission;
import ci.gouv.budget.solde.sigdcp.model.indemnite.TypeClasseVoyage;

@Getter @Setter 
@Entity
public class DossierMission extends Dossier   implements Serializable{

	private static final long serialVersionUID = 1L;

	/**
	 * La fonction du beneficiaire
	 */
	@ManyToOne
	private Fonction fonction;
	
	@ManyToOne
	private Profession profession;
	
	@Embedded
	private FraisMission frais = new FraisMission();

	@ManyToOne 
	private TypeClasseVoyage classeVoyage;
	
	@Temporal(TemporalType.DATE)
	private Date dateFinContrat;
	
	@Temporal(TemporalType.DATE)
	private Date dateRetour;
	
	public DossierMission() {
		super();
	}

	public DossierMission(Deplacement deplacement) {
		super();
		setDeplacement(deplacement);
	}
	
	public DossierMission(Courrier courrier,Date datePriseService, Deplacement deplacement, Grade grade,AgentEtat beneficiaire, FraisMission frais) {
		super(courrier, datePriseService, deplacement, grade,beneficiaire);
		this.frais = frais;
	}

}