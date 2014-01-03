package ci.gouv.budget.solde.sigdcp.controller;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.logging.Level;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;
import ci.gouv.budget.solde.sigdcp.model.AbstractModel;

@Log
public abstract class AbstractEntityFormUIController<ENTITY extends AbstractModel<?>> extends AbstractFormUIController<ENTITY> implements Serializable {

	private static final long serialVersionUID = 393104164741887088L;
	
	protected Class<ENTITY> entityClass;
	@Getter @Setter protected ENTITY entity;
	
	@SuppressWarnings("unchecked")
	public AbstractEntityFormUIController() {
		if(getClass().getGenericSuperclass() instanceof ParameterizedType)
			entityClass = (Class<ENTITY>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}
	
	@Override
	protected void initCreateOperation() {
		entity = createEntityInstance();
	}
	
	protected ENTITY createEntityInstance(){
		Class<ENTITY> clazz = entityClass;
		if(clazz!=null){
			try {
				return entityClass.newInstance();
			} catch (Exception e) {
				log.log(Level.SEVERE, e.toString(), e);
			}
		}
		return null;
	}
	
	/*
	public String action(){
		if(isCreate()){
			return createAction();
		}else if(isUpdate())
			return updateAction();
		else if(isDelete())
			return deleteAction();
		return null;
	}
	
	
	public String createAction(){
		return null;
	}
	
	public String updateAction(){
		return null;
	}
	public String deleteAction(){
		return null;
	}
	*/
	
	

}
