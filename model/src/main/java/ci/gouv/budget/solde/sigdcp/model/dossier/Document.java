/**
*    Syst�me Int�gr� de Gestion des D�penses Communes de Personnel - SIGDCP
*
*    Mod�le M�tier
*
**/


package ci.gouv.budget.solde.sigdcp.model.dossier;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Getter;
import lombok.Setter;
import ci.gouv.budget.solde.sigdcp.model.AbstractModel;
import ci.gouv.budget.solde.sigdcp.model.fichier.Fichier;
import ci.gouv.budget.solde.sigdcp.model.identification.Fonction;

@Getter @Setter 
@Entity
public class Document  extends AbstractModel<Long>  implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id @GeneratedValue
	private Long id;
	
	protected String numero;
	
	@Temporal(TemporalType.TIMESTAMP)
	protected Date dateEtablissement;
	
	@OneToOne(cascade=CascadeType.ALL)
	protected Fichier fichier = new Fichier();
	
	protected Fonction fonctionSignataire;
	
	public Document() {}

	public Document(String numero) {
		super();
		this.numero = numero;
	}
	
	
	
}