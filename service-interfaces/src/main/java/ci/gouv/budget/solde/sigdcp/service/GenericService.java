package ci.gouv.budget.solde.sigdcp.service;

import java.util.Collection;

import ci.gouv.budget.solde.sigdcp.model.AbstractModel;

public interface GenericService extends AbstractService<AbstractModel<Object>, Object> {

	<TYPE> TYPE findByClass(Class<TYPE> aClass,String identifier);
	
	<TYPE> Collection<TYPE> findAllByClass(Class<TYPE> aClass);
	
}
