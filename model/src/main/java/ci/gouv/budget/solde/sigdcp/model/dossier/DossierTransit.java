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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Getter;
import lombok.Setter;
import ci.gouv.budget.solde.sigdcp.model.identification.AgentEtat;
import ci.gouv.budget.solde.sigdcp.model.identification.Grade;

@Getter @Setter 
@Entity
public class DossierTransit extends Dossier implements Serializable{

	private static final long serialVersionUID = 1L;

	@Temporal(TemporalType.TIMESTAMP) private Date dateMiseStage;
	@Temporal(TemporalType.TIMESTAMP) private Date dateFin;
	
	private Float poidsBagagesKg;
	private Float montantFacture;
	
	//@OneToOne(cascade=CascadeType.ALL)
	//private PieceJustificative factureDefinitive;
	
	public DossierTransit() {}

	public DossierTransit(String numero, Courrier courrier,
			Date datePriseService, Deplacement deplacement, Grade grade,
			AgentEtat beneficiaire, Date dateMiseStage, Date dateFin,
			Float poidsBagagesKg, Float montantFacture) {
		super(courrier , datePriseService, deplacement,
				grade, beneficiaire);
		this.dateMiseStage = dateMiseStage;
		this.dateFin = dateFin;
		this.poidsBagagesKg = poidsBagagesKg;
		this.montantFacture = montantFacture;
	}

	public DossierTransit(Deplacement deplacement) {
		super(deplacement);
	}
	
	
}