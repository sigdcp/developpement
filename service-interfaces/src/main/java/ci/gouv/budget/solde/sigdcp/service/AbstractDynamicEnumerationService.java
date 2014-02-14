package ci.gouv.budget.solde.sigdcp.service;

import java.util.Collection;

import ci.gouv.budget.solde.sigdcp.model.DynamicEnumeration;


public interface AbstractDynamicEnumerationService<DYNAMIC_ENUMERATION extends DynamicEnumeration> extends AbstractService<DYNAMIC_ENUMERATION,String> {

	
	<TYPE> Collection<TYPE> findAllByClass(Class<TYPE> aClass);
	
	<TYPE> TYPE findByClass(Class<TYPE> aClass,String identifier);
	
}
