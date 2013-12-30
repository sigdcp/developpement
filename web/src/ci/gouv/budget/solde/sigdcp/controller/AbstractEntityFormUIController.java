package ci.gouv.budget.solde.sigdcp.controller;

import java.io.Serializable;
import java.util.logging.Level;

import lombok.Getter;
import lombok.extern.java.Log;
import ci.gouv.budget.solde.sigdcp.model.AbstractModel;

@Log
public abstract class AbstractEntityFormUIController<ENTITY extends AbstractModel<?>> extends AbstractFormUIController implements Serializable {

	private static final long serialVersionUID = 393104164741887088L;
	
	@Getter protected ENTITY entity;
	
	public AbstractEntityFormUIController() {
		//@SuppressWarnings("unchecked")
		//Class<ENTITY> entityClass = (Class<ENTITY>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
		
		try {
			entity = entityClass().newInstance();
		} catch (Exception e) {
			log.log(Level.SEVERE, e.toString(), e);
		}
	}
	
	protected abstract Class<ENTITY> entityClass();
	
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
