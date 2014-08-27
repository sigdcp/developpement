/**
*    Syst�me Int�gr� de Gestion des D�penses Communes de Personnel - SIGDCP
*
*    Mod�le M�tier
*
**/


package ci.gouv.budget.solde.sigdcp.model.traitement;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import ci.gouv.budget.solde.sigdcp.model.AbstractModel;
import ci.gouv.budget.solde.sigdcp.model.dossier.ValidationType;
import ci.gouv.budget.solde.sigdcp.model.identification.Role;

@Getter @Setter 
@Entity 
@Table(name="OPVALIDCONF")
public class OperationValidationConfig  extends AbstractModel<Long>  implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id @GeneratedValue
	private Long id;
	
	@ManyToOne
	private NatureOperation natureOperation;
	
	@Enumerated(EnumType.STRING)
	private ValidationType validationType;
	
	@ManyToMany
	//@JoinTable(name="OPTROLE")
	@JoinTable(name="OPTROLE",joinColumns = { @JoinColumn(name = "OPVALCONFCODE") } ,inverseJoinColumns={ @JoinColumn(name = "ROLECODE") })
	private Set<Role> roles = new HashSet<>();
	
	private Boolean enabled = Boolean.TRUE;
	
	@ManyToOne
	private Statut statutResultat;
	
	public OperationValidationConfig() {}

	public OperationValidationConfig(NatureOperation natureOperation, ValidationType validationType, Statut statutResultat,Role...roles) {
		super();
		this.natureOperation = natureOperation;
		this.validationType = validationType;
		this.statutResultat = statutResultat;
		if(roles!=null)
			for(Role role : roles)
				this.roles.add(role);
	}
	
	

}