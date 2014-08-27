/**
*    Syst�me Int�gr� de Gestion des D�penses Communes de Personnel - SIGDCP
*
*    Mod�le M�tier
*
**/


package ci.gouv.budget.solde.sigdcp.model.identification;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import ci.gouv.budget.solde.sigdcp.model.AbstractModel;
import ci.gouv.budget.solde.sigdcp.model.geographie.Contact;
import ci.gouv.budget.solde.sigdcp.model.utils.validation.groups.Client;

@Getter @Setter 
@Entity @Inheritance(strategy=InheritanceType.SINGLE_TABLE) @EqualsAndHashCode(of="id",callSuper=false)
@Table(name="requerant")
public class Party extends AbstractModel<Long>  implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id @GeneratedValue
	private Long id;
	
	@NotNull(groups=Client.class)
	@Column(nullable=false)
	protected String nom;
	
	@OneToOne(cascade=CascadeType.ALL) @Valid
	private Contact contact = new Contact();
	
	public Party() {}
 
	public Party(String nom,Contact contact) {
		this.nom = nom; 
		this.contact = contact;
	}
	
	
}