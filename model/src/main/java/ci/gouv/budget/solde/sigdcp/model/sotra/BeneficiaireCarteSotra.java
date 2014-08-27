/**
*    Syst�me Int�gr� de Gestion des D�penses Communes de Personnel - SIGDCP
*
*    Mod�le M�tier
*
**/


package ci.gouv.budget.solde.sigdcp.model.sotra;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Getter;
import lombok.Setter;
import ci.gouv.budget.solde.sigdcp.model.AbstractModel;
import ci.gouv.budget.solde.sigdcp.model.identification.AgentEtat;
import ci.gouv.budget.solde.sigdcp.model.identification.DelegueSotra;

@Getter @Setter 
@Entity
@Table(name="BENEFCS")
//@Table(uniqueConstraints = {@UniqueConstraint(columnNames = { "username", "site" })})
public class BeneficiaireCarteSotra extends AbstractModel<Long>   implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id @GeneratedValue private Long id;
	
	@JoinColumn(name="delegue")
	@ManyToOne private DelegueSotra delegue;
	
	@JoinColumn(name="agent")
	@ManyToOne private AgentEtat agent;
	
	@Temporal(TemporalType.DATE)
	private Date dateInscription;
	
	@Temporal(TemporalType.DATE)
	private Date dateValidation;
	
	private Boolean valide;
	
	public BeneficiaireCarteSotra() {
		super();
	}

	public BeneficiaireCarteSotra(DelegueSotra delegue, AgentEtat agent,
			Date dateInscription) {
		super();
		this.delegue = delegue;
		this.agent = agent;
		this.dateInscription = dateInscription;
	}

	@Override
	public String toString() {
		return "AdherentCarteSotra [delegueSotra=" + delegue + ", agent="
				+ agent + ", dateInscription=" + dateInscription
				+ ", dateValidation=" + dateValidation + ", valide=" + valide
				+ "]";
	}

	
	
	
	
	
}