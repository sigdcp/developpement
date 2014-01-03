/**
*    Syst�me Int�gr� de Gestion des D�penses Communes de Personnel - SIGDCP
*
*    Mod�le M�tier
*
**/


package ci.gouv.budget.solde.sigdcp.model.dossier;

import java.io.Serializable;

import javax.persistence.Entity;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter 
@Entity
//TODO to be renamed to DossierMission
public class DossierMHCI  extends Dossier   implements Serializable{

	private static final long serialVersionUID = 1L;

}