package ci.gouv.budget.solde.sigdcp.model.identification;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ci.gouv.budget.solde.sigdcp.model.AbstractModel;
import ci.gouv.budget.solde.sigdcp.model.geographie.Localite;

@Entity @Getter @Setter 
@AllArgsConstructor @NoArgsConstructor @EqualsAndHashCode(of="matricule",callSuper=false)
@Table(name="AGENTETATREF")
public class AgentEtatReference extends AbstractModel<String> implements Serializable {

	private static final long serialVersionUID = 4242485379623860202L;

	@Id private String matricule;
	
	@Column(nullable=false)
	private String nomPrenoms;
	
	@Temporal(TemporalType.DATE)
	private Date dateNaissance;
	
	@Temporal(TemporalType.DATE)
	private Date dateRecrutement;
	
	@Temporal(TemporalType.DATE)
	private Date datePriseService;
	
	@ManyToOne
	private Grade grade;
	
	@ManyToOne
	private Echelon echelon;
	
	@ManyToOne
	private Profession profession;
	
	@ManyToOne
	private Fonction fonction;
	
	@ManyToOne
	private TypeAgentEtat type;
	
	@ManyToOne
	private Localite nationalite;
	
}
