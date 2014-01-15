/**
*    Syst�me Int�gr� de Gestion des D�penses Communes de Personnel - SIGDCP
*
*    Mod�le M�tier
*
**/


package ci.gouv.budget.solde.sigdcp.model.identification;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;
import ci.gouv.budget.solde.sigdcp.model.geographie.Contact;
import ci.gouv.budget.solde.sigdcp.model.geographie.Localite;

@Getter @Setter 
@Entity
public class DelegueSotra  extends AgentEtat   implements Serializable{

	private static final long serialVersionUID = 1L;

	@ManyToOne private AgentEtat interimaire;
	@ManyToOne private Section sectionGeree;
	
	public DelegueSotra() {
		super();
	}

	public DelegueSotra(String code, String matricule, String nom,
			String prenoms, Date dateNaissance, Contact contact, Sexe sexe,
			SituationMatrimoniale situationMatrimoniale, Localite nationalite,
			Date dateCreation, Grade grade, Echelon echelon, Position position,
			Integer indice, Fonction fonction, Section section,
			Profession profession, Personne ayantDroit, AgentEtat interimaire,
			Section sectionGeree) {
		super(code, matricule, nom, prenoms, dateNaissance, contact, sexe,
				situationMatrimoniale, nationalite, dateCreation, grade,
				echelon, position, indice, fonction, section, profession,
				ayantDroit);
		this.interimaire = interimaire;
		this.sectionGeree = sectionGeree;
	}

	

	
	
}