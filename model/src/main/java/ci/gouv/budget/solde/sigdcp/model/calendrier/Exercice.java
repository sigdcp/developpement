/**
*    Syst�me Int�gr� de Gestion des D�penses Communes de Personnel - SIGDCP
*
*    Mod�le M�tier
*
**/


package ci.gouv.budget.solde.sigdcp.model.calendrier;

import java.util.Date;

import ci.gouv.budget.solde.sigdcp.model.AbstractModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Getter @Setter 
@Entity @AllArgsConstructor
public class Exercice  extends AbstractModel<Integer>  implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	private Integer annee;
	@Temporal(TemporalType.TIMESTAMP)
	private Date debut;
	@Temporal(TemporalType.TIMESTAMP)
	private Date fin;
	private String libelle;
	private Boolean statut;
	
	public Exercice() {}
}