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

@Getter @Setter 
@Entity
public class DossierObseques  extends Dossier   implements Serializable{

	private static final long serialVersionUID = 1L;

	private String nomAgentConstataire;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateDeces;
	
	private Boolean autopsie;
	
	private Float coutTransport;
	
	@ManyToOne
	private Localite lieuDeces;
	
	@ManyToOne
	private CauseDeces causeDeces;
	
	public DossierObseques() {}

	
}