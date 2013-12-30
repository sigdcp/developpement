/**
*    Syst�me Int�gr� de Gestion des D�penses Communes de Personnel - SIGDCP
*
*    Mod�le M�tier
*
**/


package ci.gouv.budget.solde.sigdcp.model.dossier;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Getter;
import lombok.Setter;
import ci.gouv.budget.solde.sigdcp.model.AbstractModel;
import ci.gouv.budget.solde.sigdcp.model.fichier.Fichier;

@Getter @Setter 
@Entity
public class PieceJustificative  extends AbstractModel<Long>  implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id @GeneratedValue
	private Long id;
	
	private String numero;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateEtablissement;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateEffetDecision;
	
	@OneToOne(cascade=CascadeType.ALL)
	private Fichier fichier = new Fichier();
	
	private String fonctionSignataire;
	
	@ManyToOne
	private PieceJustificativeAFournir model;
	
	@ManyToOne
	private Dossier dossier;
	
	public PieceJustificative() {}

	public PieceJustificative(PieceJustificativeAFournir model,Dossier dossier) {
		super();
		this.model = model;
		this.dossier = dossier;
	}

	public PieceJustificative(String numero, PieceJustificativeAFournir model) {
		super();
		this.numero = numero;
		this.model = model;
	}
	
	
	
}