/**
*    Syst�me Int�gr� de Gestion des D�penses Communes de Personnel - SIGDCP
*
*    Mod�le M�tier
*
**/


package ci.gouv.budget.solde.sigdcp.model.dossier;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;
import ci.gouv.budget.solde.sigdcp.model.AbstractModel;

@Getter @Setter 
@Entity
public class Traitement  extends AbstractModel<Long>  implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id @GeneratedValue
	private Long id;
	
	@ManyToOne
	private Operation operation;
	
	@ManyToOne
	private PieceProduite pieceProduite;
	
	@ManyToOne
	private Dossier dossier;
	
	@ManyToOne
	private Statut statut;
	
	@ManyToOne
	private Motif motif;
	
	private String observation;
	
	private ValidationType validationType;
	
	public Traitement() {}

	public Traitement(Operation operation,
			PieceProduite pieceProduite, Dossier dossier, Statut statut) {
		super();
		this.operation = operation;
		this.pieceProduite = pieceProduite;
		this.dossier = dossier;
		this.statut = statut;
	}
	
	
}