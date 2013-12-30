/**
*    Syst�me Int�gr� de Gestion des D�penses Communes de Personnel - SIGDCP
*
*    Mod�le M�tier
*
**/


package ci.gouv.budget.solde.sigdcp.model.geographie;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;

import org.hibernate.validator.constraints.Email;

@Getter @Setter 
@Embeddable @AllArgsConstructor
public class Contact implements Serializable{

	private static final long serialVersionUID = 1L;

	@Email
	private String email;
	
	private String telephone;
	
	private String boitePostale;
	
	private String adresse;
	
	@ManyToOne
	private Localite situationGeographique;
	
	public Contact() {}
}