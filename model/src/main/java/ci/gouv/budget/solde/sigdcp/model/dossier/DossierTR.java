/**
*    Syst�me Int�gr� de Gestion des D�penses Communes de Personnel - SIGDCP
*
*    Mod�le M�tier
*
**/


package ci.gouv.budget.solde.sigdcp.model.dossier;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Getter;
import lombok.Setter;
import ci.gouv.budget.solde.sigdcp.model._validation.OrderedDate;

@Getter @Setter 
@Entity
@OrderedDate(first="datePriseService",second="dateFin",message="Date mal ordone")
public class DossierTR extends Dossier implements Serializable{

	private static final long serialVersionUID = 1L;

	@Temporal(TemporalType.TIMESTAMP)
	private Date dateMiseStage;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateFin;
	
	private Float poidsBagagesKg;
	
	private Float montantFacture;
}