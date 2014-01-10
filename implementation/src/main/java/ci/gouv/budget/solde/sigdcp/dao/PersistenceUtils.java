package ci.gouv.budget.solde.sigdcp.dao;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Singleton;
import javax.persistence.Id;

import lombok.extern.java.Log;

@Singleton @Log
public class PersistenceUtils implements Serializable {

	private static final long serialVersionUID = -6429158098036314066L;
	
	public List<Field> getAllFields(List<Field> fields, Class<?> type) {
	    for (Field field: type.getDeclaredFields()) {
	        fields.add(field);
	    }

	    if (type.getSuperclass() != null) {
	        fields = getAllFields(fields, type.getSuperclass());
	    }

	    return fields;
	}
	
	public String identifierFieldName(Class<?> aClass){
		
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

}
