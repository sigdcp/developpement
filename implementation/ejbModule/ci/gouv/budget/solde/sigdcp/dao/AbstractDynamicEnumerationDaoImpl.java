package ci.gouv.budget.solde.sigdcp.dao;

import java.io.Serializable;
import java.util.List;

import ci.gouv.budget.solde.sigdcp.model.DynamicEnumeration;

public abstract class AbstractDynamicEnumerationDaoImpl<DYNAMIC_ENUMERATION extends DynamicEnumeration> extends JpaDaoImpl<DYNAMIC_ENUMERATION, String> 
	implements AbstractDynamicEnumerationDao<DYNAMIC_ENUMERATION> , Serializable {

	private static final long serialVersionUID = -2609724288199083806L;
	
	@Override
	public <TYPE> List<TYPE> readAllByClass(Class<TYPE> aClass) {
		return (List<TYPE>) entityManager.createQuery("SELECT entity FROM "+aClass.getSimpleName()+" entity", aClass)
				.getResultList();
	}

	
}

