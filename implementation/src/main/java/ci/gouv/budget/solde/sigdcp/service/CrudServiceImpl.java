package ci.gouv.budget.solde.sigdcp.service;

import java.io.Serializable;
import java.util.Collection;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import ci.gouv.budget.solde.sigdcp.dao.CrudDao;
import ci.gouv.budget.solde.sigdcp.model.DynamicEnumeration;

@Stateless
public class CrudServiceImpl implements CrudService , Serializable {

	private static final long serialVersionUID = -7601857525393731774L;
	
	@Inject private CrudDao dao;

	@Override
	public Object enregistrer(Object object) {
		if(object instanceof DynamicEnumeration){
			if( StringUtils.isEmpty(((DynamicEnumeration)object).getCode()))
				throw new ServiceException("Un code est obligatoire");
		}
		return dao.update(object);
	}

	@Override
	public void supprimer(Collection<?> objects) {
		for(Object o : objects)
			dao.delete(o);
	}

	@Override
	public Boolean existe(Class<?> clazz, Object id) {
		return dao.existe(clazz, id);
	}
	
	

}

