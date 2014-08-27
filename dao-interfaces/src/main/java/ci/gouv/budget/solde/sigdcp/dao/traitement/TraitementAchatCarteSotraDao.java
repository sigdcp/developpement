package ci.gouv.budget.solde.sigdcp.dao.traitement;

import java.util.Collection;

import ci.gouv.budget.solde.sigdcp.model.sotra.AchatCarteSotra;
import ci.gouv.budget.solde.sigdcp.model.traitement.TraitementAchatCarteSotra;

public interface TraitementAchatCarteSotraDao extends AbstractTraitementDao<TraitementAchatCarteSotra> {

	Collection<TraitementAchatCarteSotra> readByAchatByNatureOperationIdByStatutId(AchatCarteSotra achat,String natureOperationId,String statutId);
	
}
