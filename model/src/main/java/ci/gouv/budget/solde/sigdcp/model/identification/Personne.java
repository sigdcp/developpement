/**
*    Syst�me Int�gr� de Gestion des D�penses Communes de Personnel - SIGDCP
*
*    Mod�le M�tier
*
**/


package ci.gouv.budget.solde.sigdcp.model.identification;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;
import ci.gouv.budget.solde.sigdcp.model.geographie.Contact;
import ci.gouv.budget.solde.sigdcp.model.geographie.Localite;
import ci.gouv.budget.solde.sigdcp.model.utils.validation.groups.Client;

@Getter @Setter 
@Entity
@Inheritance(strategy=InheritanceType.JOINED)
public class Personne  extends Party  implements Serializable{

	private static final long serialVersionUID = 1L;
	
	public enum PieceIdentiteType{
		CNI{@Override public String toString() {return "Carte nationale d'identite";}},
		PASSPORT{@Override public String toString() {return "Passport";}},
		ATTESTATION{@Override public String toString() {return "Attestation d'identite";}}
		}
	
	private String prenoms;
	
	@Temporal(TemporalType.TIMESTAMP)
	@NotNull(groups=Client.class)
	private Date dateNaissance;
	
	@Enumerated(EnumType.ORDINAL)
	@NotNull(groups=Client.class)
	@Column(nullable=false)
	private Sexe sexe;
	
	@ManyToOne
	private SituationMatrimoniale situationMatrimoniale;
	
	@ManyToOne
	@NotNull(groups=Client.class)
	private Localite nationalite;
	
	@ManyToOne
	private Profession profession;
	
	/*
	 * Infos piece identite
	 */
	//TODO think more
	private String pieceIdentiteNumero;
	private PieceIdentiteType pieceIdentiteType;
	
	public Personne() {}
	
	public Personne(String nom, String prenoms,
			Date dateNaissance, Contact contact, Sexe sexe,
			SituationMatrimoniale situationMatrimoniale, Localite nationalite,
			Profession profession,Date dateCreation) {
		super(nom,contact,dateCreation);
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