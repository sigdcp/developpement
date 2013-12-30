/**
*    Syst�me Int�gr� de Gestion des D�penses Communes de Personnel - SIGDCP
*
*    Mod�le M�tier
*
**/


package ci.gouv.budget.solde.sigdcp.model.prestation;

import java.util.Date;

import ci.gouv.budget.solde.sigdcp.model.dossier.Deplacement;
import ci.gouv.budget.solde.sigdcp.model.AbstractModel;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Getter @Setter 
@Entity
public class Commande  extends AbstractModel<String>  implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	private String numero; 
	@Temporal(TemporalType.TIMESTAMP)
	private Date date;
	private Integer quantite;
	@ManyToOne
	private TypeCommande type;
	@ManyToOne
	private Deplacement deplacement;
	@ManyToOne
	private Prestataire prestataire;
}