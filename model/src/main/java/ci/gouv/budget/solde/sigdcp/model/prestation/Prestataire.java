/**
*    Syst�me Int�gr� de Gestion des D�penses Communes de Personnel - SIGDCP
*
*    Mod�le M�tier
*
**/


package ci.gouv.budget.solde.sigdcp.model.prestation;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Getter;
import lombok.Setter;
import ci.gouv.budget.solde.sigdcp.model.geographie.Contact;
import ci.gouv.budget.solde.sigdcp.model.identification.Party;

@Getter @Setter 
@Entity
public class Prestataire  extends Party  implements Serializable{

	private static final long serialVersionUID = 1L;

	@Temporal(TemporalType.TIMESTAMP)
	private Date dateHabilitation;
	
	private Boolean habilitationValide;
	
	public Prestataire() {}

	public Prestataire(String code, String nom, Contact contact,Date dateCreation) {
		super(code,nom,contact,dateCreation);
	}
	
	
}