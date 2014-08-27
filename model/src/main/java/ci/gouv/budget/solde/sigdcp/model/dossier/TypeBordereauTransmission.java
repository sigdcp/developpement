/**
*    Syst�me Int�gr� de Gestion des D�penses Communes de Personnel - SIGDCP
*
*    Mod�le M�tier
*
**/


package ci.gouv.budget.solde.sigdcp.model.dossier;

import java.io.Serializable;

import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter 
//@Entity 
@Table(name="TYPEBT") @Deprecated
public class TypeBordereauTransmission  extends TypePieceProduite  implements Serializable{

	private static final long serialVersionUID = 1L;
	
	public TypeBordereauTransmission() {}
	
	
}