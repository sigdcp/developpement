/**
*    Syst�me Int�gr� de Gestion des D�penses Communes de Personnel - SIGDCP
*
*    Mod�le M�tier
*
**/


package ci.gouv.budget.solde.sigdcp.model.identification;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ci.gouv.budget.solde.sigdcp.model.AbstractModel;

@Getter @Setter 
@Entity @AllArgsConstructor
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@Table(name="SOUSCRIPTION")
public class Souscription  extends AbstractModel<String>  implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id private String code;
	
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
	private Boolean acceptee;
		
	public Souscription() {}
}