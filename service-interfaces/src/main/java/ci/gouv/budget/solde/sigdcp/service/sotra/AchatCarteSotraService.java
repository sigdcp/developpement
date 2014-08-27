package ci.gouv.budget.solde.sigdcp.service.sotra;

import ci.gouv.budget.solde.sigdcp.model.sotra.AchatCarteSotra;
import ci.gouv.budget.solde.sigdcp.service.traitement.TraitableService;

public interface AchatCarteSotraService extends TraitableService<AchatCarteSotra,Long> {

	void annuler(AchatCarteSotra achat);
	
}
