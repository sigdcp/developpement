/**
*    Syst�me Int�gr� de Gestion des D�penses Communes de Personnel - SIGDCP
*
*    Mod�le M�tier
*
**/


package ci.gouv.budget.solde.sigdcp.model.dossier;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import ci.gouv.budget.solde.sigdcp.model.AbstractModel;
import ci.gouv.budget.solde.sigdcp.model.identification.AgentEtat;
import ci.gouv.budget.solde.sigdcp.model.identification.Grade;
import ci.gouv.budget.solde.sigdcp.model.identification.Section;
import ci.gouv.budget.solde.sigdcp.model.indemnite.Groupe;
import ci.gouv.budget.solde.sigdcp.model.indemnite.IndemniteCalculee;
import ci.gouv.budget.solde.sigdcp.model.traitement.Traitable;
import ci.gouv.budget.solde.sigdcp.model.traitement.TraitementDossier;

@Getter @Setter 
@Entity @EqualsAndHashCode(of={"id"},callSuper=false)
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
public class Dossier  extends AbstractModel<Long>  implements Serializable{

	private static final long serialVersionUID = 1L;

	/* -------------------------------------------------- Attributs Persistés --------------------------------------- */
	
	@Id @GeneratedValue(strategy=GenerationType.SEQUENCE)
	private Long id;
	
	@Embedded private Courrier courrier = new Courrier();
	
	@Temporal(TemporalType.TIMESTAMP) private Date datePriseService;
	
	@ManyToOne private Deplacement deplacement;
	
	/**
	 * Le grade au moment du deplacement
	 */
	@ManyToOne private Grade grade;
	
	@ManyToOne private AgentEtat beneficiaire;
	
	/**
	 * Le service dans lequel il est au moment ou il fait la demande
	 */
	@ManyToOne private Section service;
	
	@ManyToOne private Groupe groupe;
	
	/**
	 * Le montant total à payer pour l'indemnisation de ce dossier
	 */
	private BigDecimal montantIndemnisation;
	
	@OneToMany(fetch=FetchType.EAGER,cascade=CascadeType.ALL)
	@JoinColumn
	private Collection<IndemniteCalculee> indemniteCalculees = new ArrayList<>();
	
	@Embedded private Traitable<TraitementDossier> traitable = new Traitable<>();
	
	/* -------------------------------------------------- Attributs Non Persistés --------------------------------------- */
	
	@Transient private String numero;
	@Transient private Date dateCreation;
	@Transient private Collection<BulletinLiquidation> bulletinLiquidations = new ArrayList<>();
	@Transient private PieceJustificative feuilleDeplacement;
	@Transient private PieceJustificative pieceAdministrative;
	@Transient private Collection<PieceJustificative> pieceJustificatives;
	@Transient private Boolean marie=false;
	@Transient private Integer nombreEnfant=0;
	@Transient private TypeDepense typeDepense;
	@Transient private String messageId;
	//TODO utiliser un panier doperations supplementaire a faire 
	@Transient private Boolean transmettreBeneficiaire=false,annuler=false;
	
	public Dossier() {}

	public Dossier(Courrier courrier,Date datePriseService, Deplacement deplacement,Grade grade, AgentEtat beneficiaire) {
		super();
		this.courrier=courrier;
		this.datePriseService = datePriseService;
		this.deplacement = deplacement;
		this.grade = grade;
		this.beneficiaire = beneficiaire;
	}

	public Dossier(Deplacement deplacement) {
		super();
		this.deplacement = deplacement;
	}
	
	public TraitementDossier traitementPieceProduite(String codePieceProduite){
		for(TraitementDossier td : traitable.getHistoriques()){
			if(td.getPieceProduite()!=null && codePieceProduite.equals(td.getPieceProduite().getType().getCode()))
				return td;
		}
		return null;
	}
	
}