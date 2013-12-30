/**
*    Syst�me Int�gr� de Gestion des D�penses Communes de Personnel - SIGDCP
*
*    Mod�le M�tier
*
**/


package ci.gouv.budget.solde.sigdcp.model.dossier;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import ci.gouv.budget.solde.sigdcp.model.AbstractModel;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter 
@Entity
public class Visa extends AbstractModel<VisaId> implements Serializable{

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private VisaId id;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date date;
	
}