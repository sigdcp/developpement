/**
*    Syst�me Int�gr� de Gestion des D�penses Communes de Personnel - SIGDCP
*
*    Mod�le M�tier
*
**/


package ci.gouv.budget.solde.sigdcp.model.dossier;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;
import ci.gouv.budget.solde.sigdcp.model.indemnite.Cercueil;
import ci.gouv.budget.solde.sigdcp.model.utils.validation.groups.Client;

@Getter @Setter 
@Entity
public class DossierObseques  extends Dossier   implements Serializable{

	private static final long serialVersionUID = 1L;
	
	//private String nomAgentConstataire;
	
	@NotNull(groups=Client.class)
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateDeces;
	
	@NotNull(groups=Client.class)
	private Boolean autopsie;
	
	private BigDecimal coutTransport;
	
	@NotNull(groups=Client.class)
	private String lieuDeces;
	
	@NotNull(groups=Client.class)
	@ManyToOne
	private CauseDeces causeDeces;
	
	private Integer indice=0;
	
	@ManyToOne 
	private Cercueil cercueil;
	
	public DossierObseques() {}

	public DossierObseques(Deplacement deplacement) {
		super(deplacement);
	}

	
}