/**
*    Syst�me Int�gr� de Gestion des D�penses Communes de Personnel - SIGDCP
*
*    Mod�le M�tier
*
**/


package ci.gouv.budget.solde.sigdcp.model.dossier;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import ci.gouv.budget.solde.sigdcp.model.AbstractModel;

@Getter @Setter 
@Entity @EqualsAndHashCode(of={"id"},callSuper=false)
public class Traitement  extends AbstractModel<Long>  implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id @GeneratedValue
	private Long id;
	
	@ManyToOne
	@JoinColumn(nullable=false)
	@NotNull
	private Operation operation;
	
	@ManyToOne
	private PieceProduite pieceProduite;
	
	@ManyToOne
	@JoinColumn(nullable=false)
	@NotNull
	private Dossier dossier;
	
	@ManyToOne
	@JoinColumn(nullable=false)
	@NotNull
	private Statut statut;
	
	@ManyToOne
	private Motif motif;
	
	private String observation;
	
	@Enumerated
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
	
	public Boolean isValidationTypeAccepter(){
		return ValidationType.ACCEPTER.equals(validationType);
	}
	
}