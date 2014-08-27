/**
*    Syst�me Int�gr� de Gestion des D�penses Communes de Personnel - SIGDCP
*
*    Mod�le M�tier
*
**/


package ci.gouv.budget.solde.sigdcp.model.prestation;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import lombok.Getter;
import lombok.Setter;
import ci.gouv.budget.solde.sigdcp.model.dossier.DossierObseques;
import ci.gouv.budget.solde.sigdcp.model.traitement.Traitable;
import ci.gouv.budget.solde.sigdcp.model.traitement.TraitementDemandeCotationObseque;

@Getter @Setter 
@Entity
//@Table(name="DEMCOTOBS")
public class DemandeCotationObseque  extends DemandeCotation  implements Serializable{

	private static final long serialVersionUID = 2701256888036673361L;
	
	@ManyToOne
	private DossierObseques dossier;
	
	@Transient private Collection<Prestataire> prestataires=new ArrayList<>() ;
	
	@Embedded 
	private Traitable<TraitementDemandeCotationObseque> traitable = new Traitable<>();
	
	
	public DemandeCotationObseque() {
		// TODO Auto-generated constructor stub
	}
	public DemandeCotationObseque(DossierObseques dossier) {
		super();
		this.dossier = dossier;
	}

	public DemandeCotationObseque(DossierObseques dossier,	Collection<Prestataire> prestataires) {
		super();
		this.dossier = dossier;
		this.prestataires = prestataires;
	}

	public Long getDossierId(){
		return dossier.getId();
	}

} 