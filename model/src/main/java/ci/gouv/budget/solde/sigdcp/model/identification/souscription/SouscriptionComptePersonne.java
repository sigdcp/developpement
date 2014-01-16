/**
*    Syst�me Int�gr� de Gestion des D�penses Communes de Personnel - SIGDCP
*
*    Mod�le M�tier
*
**/


package ci.gouv.budget.solde.sigdcp.model.identification.souscription;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import lombok.Getter;
import lombok.Setter;
import ci.gouv.budget.solde.sigdcp.model.dossier.NatureDeplacement;
import ci.gouv.budget.solde.sigdcp.model.identification.Souscription;

@Getter @Setter 
@Entity @DiscriminatorValue("Compte")
public class SouscriptionComptePersonne  extends Souscription  implements Serializable{

	private static final long serialVersionUID = 1L;

	/**
	 * Identification de la personne qui souscrit
	 */
	@OneToOne(cascade=CascadeType.ALL)
	private InfosSouscriptionComptePersonne personneDemandeur = new InfosSouscriptionComptePersonne();
	
	@ManyToOne
	private NatureDeplacement natureDeplacement;
	
	/**
	 * Identification de la personne référencée (peut etre le defunt dans le cas des FO) 
	 */
	@OneToOne(cascade=CascadeType.ALL)
	private InfosSouscriptionComptePersonne personneReferencee = new InfosSouscriptionComptePersonne();
		
	public SouscriptionComptePersonne() {}

	public SouscriptionComptePersonne(String code, Date dateCreation,
			Date dateValidation, Boolean acceptee,
			InfosSouscriptionComptePersonne personneDemandeur,
			NatureDeplacement natureDeplacement,
			InfosSouscriptionComptePersonne personneReferencee) {
		super(code, dateCreation, dateValidation, acceptee);
		this.personneDemandeur = personneDemandeur;
		this.natureDeplacement = natureDeplacement;
		this.personneReferencee = personneReferencee;
	}
	
	
}