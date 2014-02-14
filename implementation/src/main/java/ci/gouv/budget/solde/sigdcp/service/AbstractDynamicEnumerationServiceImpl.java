package ci.gouv.budget.solde.sigdcp.service;

import java.io.Serializable;
import java.util.Collection;

import ci.gouv.budget.solde.sigdcp.dao.AbstractDynamicEnumerationDao;
import ci.gouv.budget.solde.sigdcp.model.DynamicEnumeration;

public abstract class AbstractDynamicEnumerationServiceImpl<DYNAMIC_ENUMERATION extends DynamicEnumeration> extends DefaultServiceImpl<DYNAMIC_ENUMERATION, String> 
	implements AbstractDynamicEnumerationService<DYNAMIC_ENUMERATION> , Serializable {

	private static final long serialVersionUID = -7601857525393731774L;
	
	public AbstractDynamicEnumerationServiceImpl(AbstractDynamicEnumerationDao<DYNAMIC_ENUMERATION> dao) {
		super(dao);
	}
	
	@Override
	public <TYPE> Collection<TYPE> findAllByClass(Class<TYPE> aClass) {
		return ((AbstractDynamicEnumerationDao<DYNAMIC_ENUMERATION>)dao).readAllByClass(aClass);
	}
	
	@Override
	public <TYPE> TYPE findByClass(Class<TYPE> aClass, String identifier) {
		return ((AbstractDynamicEnumerationDao<DYNAMIC_ENUMERATION>)dao).readByClass(aClass, identifier);
	}


}

