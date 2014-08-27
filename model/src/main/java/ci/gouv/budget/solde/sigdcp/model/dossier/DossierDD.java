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
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Getter;
import lombok.Setter;
import ci.gouv.budget.solde.sigdcp.model.geographie.Localite;
import ci.gouv.budget.solde.sigdcp.model.identification.AgentEtat;
import ci.gouv.budget.solde.sigdcp.model.identification.Grade;
import ci.gouv.budget.solde.sigdcp.model.identification.Section;

@Getter @Setter 
@Entity 
public class DossierDD  extends Dossier   implements Serializable{

	private static final long serialVersionUID = 1L;

	private Integer poidsBagageConstate;	
	
	/**
	 * �quivaut � la date de mise � la retraite chez les fonctionnaires , la date de radiation chez les corps habill�s
	 */
	@Temporal(TemporalType.TIMESTAMP)
	
	private Date dateMiseRetraite;
	
	/* Infos sur famille */
	
	private String numeroRegistreMariage;
	
	private Localite mairieMariage;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateMariage;
	
	/**
	 * La date de fin de service apr�s une mutation ou une affectation
	 */
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateCessationService;
	
	@ManyToOne
	private Section serviceOrigine;
	
	public DossierDD() {}

	public DossierDD(Courrier courrier,Date datePriseService, Deplacement deplacement,
			Grade grade, AgentEtat beneficiaire, Integer poidsBagageConstate,
			Date dateMiseRetraite, String numeroRegistreMariage,Localite mairieMariage,Date dateMariage,Date dateCessationService) {
		super(courrier, datePriseService, deplacement,grade, beneficiaire);
		this.poidsBagageConstate = poidsBagageConstate;
		this.dateMiseRetraite = dateMiseRetraite;
		this.mairieMariage = mairieMariage;
		this.numeroRegistreMariage = numeroRegistreMariage;
		this.dateMariage = dateMariage;
		this.dateCessationService = dateCessationService;
	}

	public DossierDD(Deplacement deplacement) {
		super(deplacement);
	}	
	
	
	
}