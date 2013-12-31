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
import ci.gouv.budget.solde.sigdcp.model.geographie.Localite;
import ci.gouv.budget.solde.sigdcp.model.identification.AgentEtat;
import ci.gouv.budget.solde.sigdcp.model.identification.Grade;

@Getter @Setter 
@Entity 
public class DossierDD  extends Dossier   implements Serializable{

	private static final long serialVersionUID = 1L;

	private Integer poidsBagageAutorise;
	
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
	
	public DossierDD() {}

	public DossierDD(String numero, Date dateDepot, String numeroCourrier,Date datePriseService, Deplacement deplacement,
			Grade grade, AgentEtat beneficiaire,Integer poidsBagageAutorise, Integer poidsBagageConstate,
			Date dateMiseRetraite, String numeroRegistreMariage,Localite mairieMariage,Date dateMariage,Date dateCessationService) {
		super(numero, dateDepot, numeroCourrier, datePriseService, deplacement,grade, beneficiaire);
		this.poidsBagageAutorise = poidsBagageAutorise;
		this.poidsBagageConstate = poidsBagageConstate;
		this.dateMiseRetraite = dateMiseRetraite;
		this.mairieMariage = mairieMariage;
		this.numeroRegistreMariage = numeroRegistreMariage;
		this.dateMariage = dateMariage;
		this.dateCessationService = dateCessationService;
	}
	
	
}