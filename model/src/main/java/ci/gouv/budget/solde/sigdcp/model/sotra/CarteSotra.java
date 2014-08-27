/**
*    Syst�me Int�gr� de Gestion des D�penses Communes de Personnel - SIGDCP
*
*    Mod�le M�tier
*
**/


package ci.gouv.budget.solde.sigdcp.model.sotra;

import java.io.Serializable;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import ci.gouv.budget.solde.sigdcp.model.AbstractModel;
import ci.gouv.budget.solde.sigdcp.model.traitement.Traitable;
import ci.gouv.budget.solde.sigdcp.model.traitement.TraitementCarteSotra;

@Getter @Setter 
@Entity @EqualsAndHashCode(of="id",callSuper=false)
public class CarteSotra extends AbstractModel<Long>   implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id @GeneratedValue private Long id;
	
	@ManyToOne private AchatCarteSotra achat;
	@ManyToOne private BeneficiaireCarteSotra beneficiaire;
	
	@Embedded
	private Traitable<TraitementCarteSotra> traitable = new Traitable<>();
	
	public CarteSotra() {
		super();
	}

	public CarteSotra(AchatCarteSotra achat,BeneficiaireCarteSotra beneficiaire) {
		super();
		this.achat = achat;
		this.beneficiaire = beneficiaire;
	}
	
	
}