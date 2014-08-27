/**
*    Syst�me Int�gr� de Gestion des D�penses Communes de Personnel - SIGDCP
*
*    Mod�le M�tier
*
**/


package ci.gouv.budget.solde.sigdcp.model.traitement;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import ci.gouv.budget.solde.sigdcp.model.AbstractModel;
import ci.gouv.budget.solde.sigdcp.model.dossier.ValidationType;

@Getter @Setter 
@Entity @EqualsAndHashCode(of={"id"},callSuper=false)
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@Table(name="TRAITEMENT")
public class Traitement  extends AbstractModel<Long>  implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id @GeneratedValue
	private Long id;
	
	@ManyToOne
	@JoinColumn
	@NotNull
	private Operation operation;
	
	@Enumerated(EnumType.STRING)
	private ValidationType validationType;
	
	@ManyToOne
	@JoinColumn
	@NotNull
	private Statut statut;
	
	@Lob
	private String observation;
	
	@Transient private String uiLibelle;
	
	public Traitement() {}

	public Traitement(Operation operation, Statut statut) {
		super();
		this.operation = operation;
		this.statut = statut;
	}
	
	public Boolean isValidationTypeAccepter(){
		return ValidationType.ACCEPTER.equals(validationType);
	}
	
	@Override
	public String toString() {
		return uiLibelle;
	}
	
}