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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import ci.gouv.budget.solde.sigdcp.model.AbstractModel;
import ci.gouv.budget.solde.sigdcp.model.identification.AgentEtat;
import ci.gouv.budget.solde.sigdcp.model.identification.Grade;
import ci.gouv.budget.solde.sigdcp.model.identification.Section;

@Getter @Setter 
@Entity @EqualsAndHashCode(of={"numero"},callSuper=false)
public class Dossier  extends AbstractModel<String>  implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id private String numero;
	
	@Temporal(TemporalType.TIMESTAMP) private Date dateDepot;
	
	private String numeroCourrier;
	
	@Temporal(TemporalType.TIMESTAMP) private Date datePriseService;
	
	@JoinColumn(nullable=false)
	@NotNull
	@ManyToOne private Deplacement deplacement;
	
	/**
	 * Le grade au moment de la demande
	 */
	@ManyToOne private Grade grade;
	
	@JoinColumn(nullable=false)
	@NotNull
	@ManyToOne private AgentEtat beneficiaire;
	
	@ManyToOne
	private Section service;
	
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