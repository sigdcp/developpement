/**
*    Syst�me Int�gr� de Gestion des D�penses Communes de Personnel - SIGDCP
*
*    Mod�le M�tier
*
**/


package ci.gouv.budget.solde.sigdcp.model.identification.souscription;

import java.io.Serializable;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;
import ci.gouv.budget.solde.sigdcp.model.dossier.NatureDeplacement;
import ci.gouv.budget.solde.sigdcp.model.identification.ReponseSecrete;
import ci.gouv.budget.solde.sigdcp.model.identification.Souscription;
import ci.gouv.budget.solde.sigdcp.model.utils.validation.groups.Client;

@Getter @Setter 
@Entity @DiscriminatorValue("Compte")
public class SouscriptionComptePersonne  extends Souscription  implements Serializable{

	private static final long serialVersionUID = 1L;

	/**
	 * Identification de la personne qui souscrit
	 */
	@OneToOne(cascade=CascadeType.ALL) @Valid
	private InfosSouscriptionComptePersonne personneDemandeur = new InfosSouscriptionComptePersonne();
	
	@ManyToOne
	private NatureDeplacement natureDeplacement;
	
	/*
	 * Infos sur le compte utilisateur
	 */
	
	@Size(min=8,message="Le mot de passe doit avoir 8 caractères au minimum",groups=Client.class)
	@NotNull(groups=Client.class)
	private String password;
	
	@OneToMany(cascade=CascadeType.ALL,fetch=FetchType.EAGER)
	@Size(min=1,message="Veuillez saisir une reponse secrete",groups=Client.class)
	private Set<ReponseSecrete> reponseSecretes = new LinkedHashSet<>();
	
	/**
	 * Identification de la personne référencée (peut etre le defunt dans le cas des FO) 
	 */
	@OneToOne(cascade=CascadeType.ALL) @Valid
	private InfosSouscriptionComptePersonne personneReferencee;
	
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