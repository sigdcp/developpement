/**
*    Syst�me Int�gr� de Gestion des D�penses Communes de Personnel - SIGDCP
*
*    Mod�le M�tier
*
**/


package ci.gouv.budget.solde.sigdcp.model.calendrier;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Getter;
import lombok.Setter;
import ci.gouv.budget.solde.sigdcp.model.AbstractModel;
import ci.gouv.budget.solde.sigdcp.model.identification.Section;

@Getter @Setter 
//@Entity
public class CalendrierMission  extends AbstractModel<Long>  implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id @GeneratedValue private Long id;
	private Float dotation;
	@Temporal(TemporalType.TIMESTAMP) private Date dateCreation;
	private Boolean actif;
	@ManyToOne private Section section;	
	@ManyToOne private Exercice exercice;
	
	public CalendrierMission() {}
	
	public CalendrierMission(Float dotation, Boolean actif, Section section,
			Exercice exercice) {
		super();
		this.dotation = dotation;
		this.actif = actif;
		this.section = section;
		this.exercice = exercice;
	}
	
	
}