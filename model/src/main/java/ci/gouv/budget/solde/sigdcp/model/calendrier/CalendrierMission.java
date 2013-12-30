/**
*    Syst�me Int�gr� de Gestion des D�penses Communes de Personnel - SIGDCP
*
*    Mod�le M�tier
*
**/


package ci.gouv.budget.solde.sigdcp.model.calendrier;

import java.util.Date;

import ci.gouv.budget.solde.sigdcp.model.identification.Ministere;
import ci.gouv.budget.solde.sigdcp.model.AbstractModel;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Getter @Setter 
@Entity
public class CalendrierMission  extends AbstractModel<Long>  implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	private Long id;
	private Float dotation;
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateCreation;
	private Boolean actif;
	@ManyToOne
	private Ministere ministere;//TODO To be changed to section
	@ManyToOne
	private Exercice exercice;
}