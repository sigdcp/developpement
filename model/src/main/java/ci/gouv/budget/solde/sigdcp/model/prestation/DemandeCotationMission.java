/**
*    Syst�me Int�gr� de Gestion des D�penses Communes de Personnel - SIGDCP
*
*    Mod�le M�tier
*
**/


package ci.gouv.budget.solde.sigdcp.model.prestation;

import java.util.Date;

import ci.gouv.budget.solde.sigdcp.model.calendrier.Mission;
import ci.gouv.budget.solde.sigdcp.model.AbstractModel;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

@Getter @Setter 
@Entity
public class DemandeCotationMission  extends AbstractModel<Long>  implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id @GeneratedValue
	private Long id;
	
	@Temporal(TemporalType.TIMESTAMP) //@NotNull
	private Date date;
	
	private String commentaires;
	
	@ManyToOne
	private Mission mission;
} 