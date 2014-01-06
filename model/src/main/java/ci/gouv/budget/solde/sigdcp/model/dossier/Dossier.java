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
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Getter;
import lombok.Setter;
import ci.gouv.budget.solde.sigdcp.model.AbstractModel;
import ci.gouv.budget.solde.sigdcp.model.identification.AgentEtat;
import ci.gouv.budget.solde.sigdcp.model.identification.Grade;

@Getter @Setter 
@Entity 
public class Dossier  extends AbstractModel<String>  implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id private String numero;
	
	@Temporal(TemporalType.TIMESTAMP) private Date dateDepot;
	
	private String numeroCourrier;
	
	//@NotNull(message="la date de prise de service ne peut pas etre NULL")
	@Temporal(TemporalType.TIMESTAMP) private Date datePriseService;
	
	@ManyToOne private Deplacement deplacement;
	
	/**
	 * Le grade au moment de la demande
	 */
	@ManyToOne private Grade grade;
	
	@ManyToOne private AgentEtat beneficiaire;
	
	public Dossier() {}

	public Dossier(String numero, Date dateDepot, String numeroCourrier,Date datePriseService, Deplacement deplacement,Grade grade, AgentEtat beneficiaire) {
		super();
		this.numero = numero;
		this.dateDepot = dateDepot;
		this.numeroCourrier = numeroCourrier;
		this.datePriseService = datePriseService;
		this.deplacement = deplacement;
		this.grade = grade;
		this.beneficiaire = beneficiaire;
	}
	
}