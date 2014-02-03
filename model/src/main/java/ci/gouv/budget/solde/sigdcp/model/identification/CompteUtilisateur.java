/**
*    Syst�me Int�gr� de Gestion des D�penses Communes de Personnel - SIGDCP
*
*    Mod�le M�tier
*
**/


package ci.gouv.budget.solde.sigdcp.model.identification;

import java.util.Date;

import ci.gouv.budget.solde.sigdcp.model.AbstractModel;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Getter @Setter 
@Entity //TODO rename it to Compte
public class CompteUtilisateur  extends AbstractModel<Long>  implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id private Long id;
	
	@Embedded private Credentials credentials;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateCreation;
	
	private Boolean verouille = Boolean.FALSE;
	
	@ManyToOne
	private Personne personne;
}