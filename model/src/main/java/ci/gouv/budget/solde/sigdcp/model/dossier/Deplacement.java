/**
*    Syst�me Int�gr� de Gestion des D�penses Communes de Personnel - SIGDCP
*
*    Mod�le M�tier
*
**/


package ci.gouv.budget.solde.sigdcp.model.dossier;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import ci.gouv.budget.solde.sigdcp.model.AbstractModel;
import ci.gouv.budget.solde.sigdcp.model.geographie.Localite;
import ci.gouv.budget.solde.sigdcp.model.identification.Personne;
import ci.gouv.budget.solde.sigdcp.model.utils.validation.groups.Client;
import ci.gouv.budget.solde.sigdcp.model.utils.validation.groups.System;

@Getter @Setter 
@Entity @EqualsAndHashCode(of="id",callSuper=false)
public class Deplacement  extends AbstractModel<Long>  implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id @GeneratedValue
	private Long id;
	
	@JoinColumn
	@NotNull(groups=Client.class)
	@ManyToOne
	private TypeDepense typeDepense;
	
	@Column
	@NotNull(groups=System.class)
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateCreation;
	
	@JoinColumn
	@NotNull(groups=Client.class)
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateDepart;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateArrivee;
	
	//@OneToMany
	//private Collection<Imputation> imputations = new LinkedList<Imputation>();
	
	@ManyToOne @JoinColumn
	private NatureDeplacement nature;
	
	@NotNull(groups=Client.class)
	@ManyToOne
	@JoinColumn
	private Localite localiteDepart;
	
	@NotNull(groups=Client.class)
	@ManyToOne
	@JoinColumn
	private Localite localiteArrivee;
	
	@ManyToOne
	private Personne addUser;
	
	/**/
	
	@Transient private String uiLibelle;
	
	/**/
	
	public Deplacement() {}

	public Deplacement(Date dateCreation, Date dateDepart,
			Date dateArrivee, Collection<Imputation> imputations,
			NatureDeplacement nature, Localite localiteDepart,
			Localite localiteArrivee) {
		super();
		this.dateCreation = dateCreation;
		this.dateDepart = dateDepart;
		this.dateArrivee = dateArrivee;
		//this.imputations = imputations;
		this.nature = nature;
		this.localiteDepart = localiteDepart;
		this.localiteArrivee = localiteArrivee;
	}

	public Deplacement(TypeDepense typeDepense, Date dateCreation, Date dateDepart, NatureDeplacement nature, Localite localiteDepart, Localite localiteArrivee) {
		super();
		this.typeDepense = typeDepense;
		this.dateCreation = dateCreation;
		this.dateDepart = dateDepart;
		this.nature = nature;
		this.localiteDepart = localiteDepart;
		this.localiteArrivee = localiteArrivee;
	}

	public Deplacement(TypeDepense typeDepense) {
		super();
		this.typeDepense = typeDepense;
	}
	
	
	
}