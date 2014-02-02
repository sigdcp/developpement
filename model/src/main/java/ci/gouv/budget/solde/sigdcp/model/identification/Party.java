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
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;
import ci.gouv.budget.solde.sigdcp.model.AbstractModel;
import ci.gouv.budget.solde.sigdcp.model.geographie.Contact;
import ci.gouv.budget.solde.sigdcp.model.utils.validation.groups.Client;
import ci.gouv.budget.solde.sigdcp.model.utils.validation.groups.System;

@Getter @Setter 
@Entity @Inheritance(strategy=InheritanceType.JOINED)
public class Party extends AbstractModel<String>  implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id private String code;
	
	@NotNull(groups=Client.class)
	private String nom;
	
	@OneToOne(cascade=CascadeType.ALL) @Valid
	private Contact contact = new Contact();
	
	@Temporal(TemporalType.TIMESTAMP)
	@NotNull(groups=System.class)
	private Date dateCreation;
	
	public Party() {}
 
	public Party(String code, String nom,Contact contact,Date dateCreation) {
		this.code = code;
		this.nom = nom; 
		this.contact = contact;
		this.dateCreation = dateCreation;
	}
	
	
}