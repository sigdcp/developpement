/**
*    Syst�me Int�gr� de Gestion des D�penses Communes de Personnel - SIGDCP
*
*    Mod�le M�tier
*
**/


package ci.gouv.budget.solde.sigdcp.model.identification;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;
import ci.gouv.budget.solde.sigdcp.model.AbstractModel;
import ci.gouv.budget.solde.sigdcp.model.utils.validation.groups.Client;

@Getter @Setter 
@Entity
@Table(name="COMPTE")
public class CompteUtilisateur  extends AbstractModel<Long>  implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id @GeneratedValue private Long id;
	
	@Embedded private Credentials credentials = new Credentials();
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateCreation;
	
	@ManyToOne @JoinColumn(nullable=false) 
	private Party utilisateur;
	
	@ManyToMany(fetch=FetchType.EAGER)
    @JoinTable(name="USERROLES",joinColumns = { @JoinColumn(name = "USERID") } ,inverseJoinColumns={ @JoinColumn(name = "ROLE") })
    private Set<Role> roles =new HashSet<>();
	
	@Embedded
	private Verrou verrou;
	
	@OneToMany(cascade=CascadeType.ALL,fetch=FetchType.EAGER)
	@Size(min=1,message="Une reponse secrète est obligatoire",groups=Client.class)
	@JoinColumn
	private Collection<ReponseSecrete> reponseSecretes = new LinkedHashSet<>();
	
	public CompteUtilisateur() {}

	public CompteUtilisateur(Credentials credentials, Party utilisateur,Set<Role> roles) {
		super();
		this.credentials = credentials;
		this.utilisateur = utilisateur;
		this.roles = roles;
	}
	
	@Override
	public String toString() {
		return credentials.toString();
	}
	
}