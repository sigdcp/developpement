/**
*    Syst�me Int�gr� de Gestion des D�penses Communes de Personnel - SIGDCP
*
*    Mod�le M�tier
*
**/


package ci.gouv.budget.solde.sigdcp.model.dossier;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;
import ci.gouv.budget.solde.sigdcp.model.utils.validation.groups.Client;

@Getter @Setter 
@Entity
public class DossierObseques  extends Dossier   implements Serializable{

	private static final long serialVersionUID = 1L;
	
	//private String nomAgentConstataire;
	
	@NotNull(groups=Client.class)
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable=false)
	private Date dateDeces;
	
	@NotNull(groups=Client.class)
	@Column(nullable=false)
	private Boolean autopsie;
	
	private Float coutTransport;
	
	@NotNull(groups=Client.class)
	@Column(nullable=false)
	private String lieuDeces;
	
	@NotNull(groups=Client.class)
	@ManyToOne
	@JoinColumn(nullable=false)
	private CauseDeces causeDeces;
	
	public DossierObseques() {}

	
}