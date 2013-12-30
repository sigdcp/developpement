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
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ci.gouv.budget.solde.sigdcp.model.AbstractModel;
import ci.gouv.budget.solde.sigdcp.model.dossier.NatureDeplacement;

@Getter @Setter 
@Entity @AllArgsConstructor
public class Inscription  extends AbstractModel<String>  implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	private String code;
	
	/**
	 * Identification de la personne qui s'inscrit
	 */
	@OneToOne(cascade=CascadeType.ALL)
	private InfosInscriptionPersonne personneDemandeur = new InfosInscriptionPersonne();
	
	@ManyToOne
	private NatureDeplacement natureDeplacement;
	
	/**
	 * Identification de la personne référencée (peut etre le defunt dans le cas des FO) 
	 */
	@OneToOne(cascade=CascadeType.ALL)
	private InfosInscriptionPersonne personneReferencee = new InfosInscriptionPersonne();
	
	/*--------------------------------------------------------------------------------------------------*/
	
	/**
	 * Date à laquelle elle a eu lieu
	 */
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateCreation;
	
	/**
	 * Date à laquelle elle a été validée
	 * null signifie que la validation n'a pas encore eu lieu
	 */
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateValidation;
	
	/**
	 * Si l'inscription a été acceptée valeur = true
	 */
	private Boolean accepte;
		
	public Inscription() {}
}