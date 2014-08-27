package ci.gouv.budget.solde.sigdcp.service.sotra;

import java.util.Collection;

import ci.gouv.budget.solde.sigdcp.model.sotra.AchatCarteSotra;
import ci.gouv.budget.solde.sigdcp.model.sotra.CarteSotra;
import ci.gouv.budget.solde.sigdcp.service.traitement.TraitableService;

public interface CarteSotraService extends TraitableService<CarteSotra,Long> {
	
	void annuler(Collection<CarteSotra> carteSotras);
	
	Collection<CarteSotra> findByAchat(AchatCarteSotra achat);
	
}
