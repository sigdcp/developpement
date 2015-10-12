/**
*    Syst�me Int�gr� de Gestion des D�penses Communes de Personnel - SIGDCP
*
*    Mod�le M�tier
*
**/


package ci.gouv.budget.solde.sigdcp.model.dossier;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Getter;
import lombok.Setter;
import ci.gouv.budget.solde.sigdcp.model.DynamicEnumeration;
import ci.gouv.budget.solde.sigdcp.model.indemnite.RegleCalcul;

@Getter @Setter 
@Entity 
@Table(name="NATUREDEP")
public class NatureDeplacement  extends DynamicEnumeration  implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@ManyToOne
	private CategorieDeplacement categorie;
	
	@Column(name="nbjij")
	private Integer nombreJourIndemniteJournaliere;
	
	@OneToMany(fetch=FetchType.EAGER)
	//@JoinTable(name="NATDEPREGCAL") -- Mis en commentaire à cause d'Hibernate - Pb sur les cles composees
	@JoinColumn(name="NATDEP")
	private Collection<RegleCalcul> indemnites = new ArrayList<>();
	
	@Transient
	private Long nombreDossier;
	
	@Transient
	private Long montantIndemnite;
	
	@Transient 
	private Boolean sceSolde=Boolean.FALSE;
	
	public NatureDeplacement() {}

	public NatureDeplacement(CategorieDeplacement categorie,String code, String libelle,
			Integer nombreJourIndemniteJournaliere) {
		super(code, libelle);
		this.categorie = categorie;
		this.nombreJourIndemniteJournaliere = nombreJourIndemniteJournaliere;
	}

	@Override
	public String toString() {
		return libelle;
	}
	
	
}