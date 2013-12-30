package ci.gouv.budget.solde.sigdcp.dao;

import java.util.Collection;

import ci.gouv.budget.solde.sigdcp.model.DynamicEnumeration;

public interface AbstractDynamicEnumerationDao<DYNAMIC_ENUMERATION extends DynamicEnumeration> extends DataAccessObject<DYNAMIC_ENUMERATION,String> {

	
	<TYPE> TYPE readByClass(Class<TYPE> aClass,String identifier);
	
	<TYPE> Collection<TYPE> readAllByClass(Class<TYPE> aClass);
 
}
