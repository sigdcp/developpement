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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;
import ci.gouv.budget.solde.sigdcp.model.geographie.Contact;
import ci.gouv.budget.solde.sigdcp.model.geographie.Localite;
import ci.gouv.budget.solde.sigdcp.model.utils.validation.groups.Client;

/**
 * Un agent de l'état est toute personne identifiable par l'état par un matricule
 * Nous avons entre autres les fonctionnaies , les contractuels , les policier , les gendarmes , etc.
 * @author christian
 *
 */
@Getter @Setter 
@Entity
public class AgentEtat  extends Personne   implements Serializable{

	private static final long serialVersionUID = 1L;

	@NotNull(groups=Client.class)
	@Column(unique=true)
	private String matricule;
	
	@ManyToOne private Grade grade;
	
	@ManyToOne private Echelon echelon;
	
	@ManyToOne private Position position;
	
	private Integer indice;
	
	@ManyToOne private Fonction fonction;
	
	@ManyToOne private Section section;
	
	@ManyToOne private Personne ayantDroit;
	
	@NotNull(groups=Client.class)
	@JoinColumn(name="agentetat_type")
	@ManyToOne private TypeAgentEtat type;
	
	public AgentEtat() {}

	public AgentEtat(TypeAgentEtat type,String matricule, String nom, String prenoms,
			Date dateNaissance, Contact contact, Sexe sexe,
			SituationMatrimoniale situationMatrimoniale, Localite nationalite,Date dateCreation,
			Grade grade, Echelon echelon, Position position,
			Integer indice, Fonction fonction, Section section,
			Profession profession, Personne ayantDroit) {
		super(nom, prenoms, dateNaissance, contact, sexe,
				situationMatrimoniale, nationalite,profession);
		this.type = type;
		this.matricule = matricule;
		this.grade = grade;
		this.echelon = echelon;
		this.position = position;
		this.indice = indice;
		this.fonction = fonction;
		this.section = section;
		this.ayantDroit = ayantDroit;
	}
	
}