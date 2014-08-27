package ci.gouv.budget.solde.sigdcp.model.identification;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ci.gouv.budget.solde.sigdcp.model.AbstractModel;
import ci.gouv.budget.solde.sigdcp.model.geographie.Contact;

@Getter @Setter 
@Entity 
@AllArgsConstructor @NoArgsConstructor @EqualsAndHashCode(of="matricule",callSuper=false)
@Table(name="AGENTMISSREF")
public class AgentMissionReference extends AbstractModel<Long> implements Serializable {

	private static final long serialVersionUID = 4242485379623860202L;

	@Id private Long matricule;
	
	@Column(nullable=false)
	private String nom;
	@Column(nullable=false)
	private String prenoms;
	
	@Temporal(TemporalType.DATE)
	private Date dateNaissance;
	
	
	private String cni;
	
	@ManyToOne
	private Profession profession;
	
	@OneToOne(cascade=CascadeType.ALL)
	private Contact contact = new Contact();
	
}
