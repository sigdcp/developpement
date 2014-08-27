package ci.gouv.budget.solde.sigdcp.dao.prestation;

import ci.gouv.budget.solde.sigdcp.model.prestation.CommandeCarteSotra;
import ci.gouv.budget.solde.sigdcp.model.sotra.AchatCarteSotra;

public interface CommandeCarteSotraDao extends AbstractCommandeDao<CommandeCarteSotra> {

	CommandeCarteSotra readByAchat(AchatCarteSotra achat);
	
}
 