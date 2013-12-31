package ci.gouv.budget.solde.sigdcp.dao;

import java.util.Collection;

import ci.gouv.budget.solde.sigdcp.model.AbstractModel;

public interface GenericDao extends DataAccessObject<AbstractModel<Object>,Object> {
	
	<TYPE> TYPE readByClass(Class<TYPE> aClass,String identifier);
	
	<TYPE> Collection<TYPE> readAllByClass(Class<TYPE> aClass);
 
}
