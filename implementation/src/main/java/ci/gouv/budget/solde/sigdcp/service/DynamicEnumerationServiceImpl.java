package ci.gouv.budget.solde.sigdcp.service;

import java.io.Serializable;

import javax.inject.Inject;

import ci.gouv.budget.solde.sigdcp.dao.DynamicEnumerationDao;
import ci.gouv.budget.solde.sigdcp.model.DynamicEnumeration;

public class DynamicEnumerationServiceImpl extends AbstractDynamicEnumerationServiceImpl<DynamicEnumeration> implements DynamicEnumerationService , Serializable {

	private static final long serialVersionUID = -7601857525393731774L;
	
	@Inject
	public DynamicEnumerationServiceImpl(DynamicEnumerationDao dao) {
		super(dao);
		
	}
	

}

