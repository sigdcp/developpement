package ci.gouv.budget.solde.sigdcp.dao;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import ci.gouv.budget.solde.sigdcp.model.AbstractModel;

public class GenericJpaDaoImpl implements GenericDao , Serializable {

	private static final long serialVersionUID = -2609724288199083806L;
	
	//@Inject
	//private EntityManagerFactory entityManagerFactory;
	@Inject
	protected EntityManager entityManager;
	
	@Inject protected PersistenceUtils persistenceUtils;

	
	@SuppressWarnings("unchecked")
	@Override
	public <TYPE, TYPE_ID> TYPE readByClass(Class<TYPE> aClass,Class<TYPE_ID> aIdClass,String identifierAsString) {
		TYPE_ID identifier = null;
		if(String.class.equals(aIdClass))
			identifier = (TYPE_ID) identifierAsString;
		else if(Long.class.equals(aIdClass))
			identifier = (TYPE_ID) new Long(identifierAsString);
		try {
			
			return entityManager.createQuery("SELECT entity FROM "+aClass.getSimpleName()+" entity WHERE entity."+persistenceUtils.identifierFieldName(aClass)+" = :identifier", aClass)
					.setParameter("identifier", identifier)
					.getSingleResult();
		}catch (NoResultException e) {
			return null;
		}
	}
	
	@Override
	public <TYPE> TYPE readByClass(Class<TYPE> aClass, String identifier) {
		return readByClass(aClass, String.class, identifier);
	}
	
	@Override
	public <TYPE> List<TYPE> readAllByClass(Class<TYPE> aClass) {
		return (List<TYPE>) entityManager.createQuery("SELECT entity FROM "+aClass.getSimpleName()+" entity", aClass)
				.getResultList();
	}

	
	@Override
	public <TYPE> List<TYPE> readAllByClass(Class<TYPE> aClass, Integer debut, Integer max) {
		return (List<TYPE>) entityManager.createQuery("SELECT entity FROM "+aClass.getSimpleName()+" entity", aClass)
				.setFirstResult(debut).setMaxResults(max)
				.getResultList();
	}
	
	@Override
	public <TYPE> List<TYPE> readAllByClass(Class<TYPE> aClass, String critere) {
		return (List<TYPE>) entityManager.createQuery("SELECT entity FROM "+aClass.getSimpleName()+" entity ORDER  BY entity."+critere+" ASC", aClass)
				.getResultList();
	}


	@Override
	public void create(AbstractModel<Object> object) {
		throw new RuntimeException("Must not be called");
	}

	@Override
	public AbstractModel<Object> read(Object identifiant) {
		throw new RuntimeException("Must not be called");
	}

	@Override
	public AbstractModel<Object> update(AbstractModel<Object> object) {
		throw new RuntimeException("Must not be called");
	}

	@Override
	public void delete(AbstractModel<Object> object) {
		throw new RuntimeException("Must not be called");
	}

	@Override
	public Collection<AbstractModel<Object>> readAll() {
		throw new RuntimeException("Must not be called");
	}
	
	@Override
	public Boolean exist(Object identifiant) {
		throw new RuntimeException("Must not be called");
	}
	
}

