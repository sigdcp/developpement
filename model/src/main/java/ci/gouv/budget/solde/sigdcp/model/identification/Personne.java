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
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Getter;
import lombok.Setter;
import ci.gouv.budget.solde.sigdcp.model.dossier.PieceJustificative;
import ci.gouv.budget.solde.sigdcp.model.geographie.Contact;
import ci.gouv.budget.solde.sigdcp.model.geographie.Localite;

@Getter @Setter 
@Entity
@Inheritance(strategy=InheritanceType.JOINED)
public class Personne  extends Party  implements Serializable{

	private static final long serialVersionUID = 1L;

	private String prenoms;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateNaissance;
	
	@Enumerated(EnumType.ORDINAL)
	private Sexe sexe;
	
	@ManyToOne
	private SituationMatrimoniale situationMatrimoniale;
	
	@ManyToOne
	private Localite nationalite;
	
	@ManyToOne
	private Profession profession;
	
	@OneToOne(cascade=CascadeType.ALL)
	private PieceJustificative pieceIdentite = new PieceJustificative();
	
	public Personne() {}
	
	public Personne(String code, String nom, String prenoms,
			Date dateNaissance, Contact contact, Sexe sexe,
			SituationMatrimoniale situationMatrimoniale, Localite nationalite,
			Profession profession,Date dateCreation) {
		super(code, nom,contact,dateCreation);
		this.prenoms = prenoms;
		this.dateNaissance = dateNaissance;
		this.sexe = sexe;
		this.situationMatrimoniale = situationMatrimoniale;
		this.nationalite = nationalite;
		this.profession = profession;
	}
	
	public String getNomPrenoms(){
		return getNom()+" "+getPrenoms();
	}




}