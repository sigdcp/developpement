package ci.gouv.budget.solde.sigdcp.dao;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;

import org.apache.commons.lang3.StringUtils;

import ci.gouv.budget.solde.sigdcp.dao.DataAccessObject;
import ci.gouv.budget.solde.sigdcp.model.AbstractModel;

public class JpaDaoImpl<TYPE_MODEL extends AbstractModel<TYPE_IDENTIFIANT>,TYPE_IDENTIFIANT> implements DataAccessObject<TYPE_MODEL,TYPE_IDENTIFIANT> {

	@Inject
	protected EntityManager entityManager;
	protected Class<TYPE_MODEL> clazz;
	
	@SuppressWarnings("unchecked")
	@PostConstruct
	private void init(){
		clazz = (Class<TYPE_MODEL>) ((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}
	
	@Override 
	public void create(TYPE_MODEL object) {
		entityManager.persist(object);
	}

	@Override
	public TYPE_MODEL read(TYPE_IDENTIFIANT identifiant) {
		return entityManager.find(clazz, identifiant);
	}

	@Override
	public TYPE_MODEL update(TYPE_MODEL object) {
		return entityManager.merge(object);
	}

	@Override
	public void delete(TYPE_MODEL object) {
		entityManager.remove(entityManager.merge(object));
	}

	@Override
	public List<TYPE_MODEL> readAll() {
		return entityManager.createQuery("SELECT record FROM "+clazz.getSimpleName()+" record", clazz).getResultList();
	}

	@Override
	public Boolean exist(TYPE_IDENTIFIANT identifiant) {
		if(identifiant==null)
			return false;
		if(identifiant instanceof CharSequence && StringUtils.isEmpty((CharSequence) identifiant) )
			return false;
		try {
			entityManager.getReference(clazz, identifiant);
			return true;
		} catch (EntityNotFoundException e) {
			return false;
		}
	}
	
}
