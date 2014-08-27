/**
*    Syst�me Int�gr� de Gestion des D�penses Communes de Personnel - SIGDCP
*
*    Mod�le M�tier
*
**/


package ci.gouv.budget.solde.sigdcp.model.indemnite;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import ci.gouv.budget.solde.sigdcp.model.AbstractModel;

@Getter @Setter 
@Entity
@Table(name="CLMOYENTRANS")
public class ClasseMoyenTransport extends AbstractModel<ClasseMoyenTransportId>    implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	private ClasseMoyenTransportId id;
	
}