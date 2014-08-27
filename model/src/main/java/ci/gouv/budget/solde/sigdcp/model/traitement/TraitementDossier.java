/**
*    Syst�me Int�gr� de Gestion des D�penses Communes de Personnel - SIGDCP
*
*    Mod�le M�tier
*
**/


package ci.gouv.budget.solde.sigdcp.model.traitement;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;
import ci.gouv.budget.solde.sigdcp.model.dossier.Dossier;
import ci.gouv.budget.solde.sigdcp.model.dossier.PieceProduite;

@Getter @Setter 
@Entity
//@Table(name="TRAITDOSSIER")
public class TraitementDossier  extends Traitement  implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@ManyToOne
	@JoinColumn
	@NotNull
	private Dossier dossier;
	
	@ManyToOne
	private PieceProduite pieceProduite;
	
	public TraitementDossier() {}

	public TraitementDossier(Operation operation, PieceProduite pieceProduite, Statut statut, Dossier dossier) {
		super(operation, statut);
		this.dossier = dossier;
		this.pieceProduite = pieceProduite;
	}

	

//	
}