package ci.gouv.budget.solde.sigdcp.dao.traitement;

import java.util.Collection;

import ci.gouv.budget.solde.sigdcp.dao.DataAccessObject;
import ci.gouv.budget.solde.sigdcp.model.AbstractModel;

public interface TraitableDao<TRAITABLE extends AbstractModel<TID>,TID> extends DataAccessObject<TRAITABLE,TID> {
	
	Collection<TRAITABLE> readByNatureOperationIdByStatutId(String natureOperationId,String statutId);
	

} 
