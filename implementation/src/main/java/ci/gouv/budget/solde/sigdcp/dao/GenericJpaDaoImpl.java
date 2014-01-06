package ci.gouv.budget.solde.sigdcp.dao;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Id;
import javax.persistence.NoResultException;

import lombok.extern.java.Log;
import ci.gouv.budget.solde.sigdcp.model.AbstractModel;

@Log
public class GenericJpaDaoImpl implements GenericDao , Serializable {

	private static final long serialVersionUID = -2609724288199083806L;
	
	//@Inject
	//private EntityManagerFactory entityManagerFactory;
	@Inject
	protected EntityManager entityManager;
	
	public static List<Field> getAllFields(List<Field> fields, Class<?> type) {
	    for (Field field: type.getDeclaredFields()) {
	        fields.add(field);
	    }

	    if (type.getSuperclass() != null) {
	        fields = getAllFields(fields, type.getSuperclass());
	    }

	    return fields;
	}
	
	protected String identifierFieldName(Class<?> aClass){
		
		//for(EntityType<?> entityType : entityManagerFactory.getMetamodel().getEntities())
		//	if(entityType.getJavaType().equals(aClass))
		//		return entityType.get
		
		List<Field> fields = new ArrayList<>();
		getAllFields(fields, aClass);
		Annotation idAnnotation;
		for(Field field : fields){
			idAnnotation = field.getAnnotation(Id.class);
			if(idAnnotation!=null)
				return field.getName();
		}
		log.severe("JPA Id not found for "+aClass);
		return null;
	}
	
	@Override
	public <TYPE> TYPE readByClass(Class<TYPE> aClass,String identifier) {
		try {
			return entityManager.createQuery("SELECT entity FROM "+aClass.getSimpleName()+" entity WHERE entity."+identifierFieldName(aClass)+" = :identifier", aClass)
					.setParameter("identifier", identifier)
					.getSingleResult();
		}catch (NoResultException e2) {
			return null;
		}
	}
	
	@Override
	public <TYPE> List<TYPE> readAllByClass(Class<TYPE> aClass) {
		return (List<TYPE>) entityManager.createQuery("SELECT entity FROM "+aClass.getSimpleName()+" entity", aClass)
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
	
}

