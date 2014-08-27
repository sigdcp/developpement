/**
*    Syst�me Int�gr� de Gestion des D�penses Communes de Personnel - SIGDCP
*
*    Mod�le M�tier
*
**/


package ci.gouv.budget.solde.sigdcp.model.sotra;

import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import ci.gouv.budget.solde.sigdcp.model.AbstractModel;
import ci.gouv.budget.solde.sigdcp.model.identification.DelegueSotra;
import ci.gouv.budget.solde.sigdcp.model.prestation.CommandeCarteSotra;
import ci.gouv.budget.solde.sigdcp.model.traitement.Traitable;
import ci.gouv.budget.solde.sigdcp.model.traitement.TraitementAchatCarteSotra;

@Getter @Setter 
@Entity @EqualsAndHashCode(of="id",callSuper=false)
@Table(name="ACHATCS")
public class AchatCarteSotra extends AbstractModel<Long>   implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id @GeneratedValue private Long id;
	
	@ManyToOne private DelegueSotra delegue;
	
	@Column(nullable=false) @NotNull
	private Integer mois;
	
	@Column(nullable=false) @NotNull
	private Integer annee;
	
	@Embedded
	private Traitable<TraitementAchatCarteSotra> traitable = new Traitable<>();
	
	@Transient private CommandeCarteSotra bonCommande;
	
	/* Données non persistées */
	
	@Transient private Integer nombreCarte;
	@Transient private Set<Integer> moisChoix=new LinkedHashSet<>();
	@Transient private Set<Integer> anneesChoix=new LinkedHashSet<>();
	@Transient private CommandeCarteSotra commande;
	@Transient private Collection<CarteSotra> cartes;
	
	
	public AchatCarteSotra() {
		super();
	}
	
	
}