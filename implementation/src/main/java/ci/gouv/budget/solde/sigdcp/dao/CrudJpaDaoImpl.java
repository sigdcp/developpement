package ci.gouv.budget.solde.sigdcp.dao;

import java.io.Serializable;

import javax.inject.Inject;
import javax.persistence.EntityManager;

public class CrudJpaDaoImpl implements CrudDao , Serializable {

	private static final long serialVersionUID = -2609724288199083806L;

	@Inject
	protected EntityManager entityManager;
	
	@Override 
	public void create(Object object) {
		entityManager.persist(object);
	}

	@Override
	public Object update(Object object) {
		return entityManager.merge(object);
	}

	@Override
	public void delete(Object object) {
		entityManager.remove(entityManager.merge(object));
	}

	@Override
	public Boolean existe(Class<?> clazz, Object id) {
		try {
			return entityManager.find(clazz, id)!=null;
		} catch (Exception e) {
			return false;
		}
	}
	
}

