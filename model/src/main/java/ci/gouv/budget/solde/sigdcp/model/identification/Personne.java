/**
*    Syst�me Int�gr� de Gestion des D�penses Communes de Personnel - SIGDCP
*
*    Mod�le M�tier
*
**/


package ci.gouv.budget.solde.sigdcp.model.identification;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ci.gouv.budget.solde.sigdcp.model.AbstractModel;
import ci.gouv.budget.solde.sigdcp.model.geographie.Contact;
import ci.gouv.budget.solde.sigdcp.model.geographie.Localite;

@Getter @Setter 
@Entity @AllArgsConstructor
@Inheritance(strategy=InheritanceType.JOINED)
public class Personne  extends AbstractModel<String>  implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	private String code;
	private String nom;
	private String prenoms;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateNaissance;
	
	@OneToOne(cascade=CascadeType.ALL)
	private Contact contact = new Contact();
	
	@Enumerated(EnumType.ORDINAL)
	private Sexe sexe;
	
	@ManyToOne
	private SituationMatrimoniale situationMatrimoniale;
	
	@ManyToOne
	private Localite nationalite;
	
	@ManyToOne
	private Profession profession;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateCreation;
	
	public Personne() {}
	
	public String getNomPrenoms(){
		return getNom()+" "+getPrenoms();
	}
}