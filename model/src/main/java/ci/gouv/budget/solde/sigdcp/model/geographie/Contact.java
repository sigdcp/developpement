/**
*    Syst�me Int�gr� de Gestion des D�penses Communes de Personnel - SIGDCP
*
*    Mod�le M�tier
*
**/


package ci.gouv.budget.solde.sigdcp.model.geographie;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

import org.hibernate.validator.constraints.Email;

import ci.gouv.budget.solde.sigdcp.model.AbstractModel;
import ci.gouv.budget.solde.sigdcp.model.utils.validation.groups.Client;

@Getter @Setter 
@Entity
public class Contact extends AbstractModel<Long> implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id @GeneratedValue
	private Long id;
	
	@Email(groups=Client.class)
	@NotNull(message="adresse email",groups=Client.class)
	private String email;
	
	private String telephone;
	
	private String boitePostale;
	
	private String adresse;
	
	@ManyToOne
	private Localite situationGeographique;
	
	public Contact() {}

	public Contact(String email, String telephone, String boitePostale,
			String adresse, Localite situationGeographique) {
		super();
		this.email = email;
		this.telephone = telephone;
		this.boitePostale = boitePostale;
		this.adresse = adresse;
		this.situationGeographique = situationGeographique;
	}
	
	public Contact(Contact contact){
		this(contact.email,contact.telephone,contact.boitePostale,contact.adresse,contact.situationGeographique);
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return email+" / "+telephone+" / "+boitePostale;
	}
}