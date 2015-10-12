package ci.gouv.budget.solde.sigdcp.dao;

import java.util.Collection;

import ci.gouv.budget.solde.sigdcp.model.AbstractModel;

public interface GenericDao extends DataAccessObject<AbstractModel<Object>,Object> {
	
	<TYPE, TYPE_ID> TYPE readByClass(Class<TYPE> aClass,Class<TYPE_ID> aIdClass,String identifier);
	
	<TYPE> TYPE readByClass(Class<TYPE> aClass,String identifier);
	
	<TYPE> Collection<TYPE> readAllByClass(Class<TYPE> aClass);

	<TYPE> Collection<TYPE> readAllByClass(Class<TYPE> aClass, Integer debut, Integer max);
	<TYPE> Collection<TYPE> readAllByClass(Class<TYPE> aClass, String critere);
 
}
