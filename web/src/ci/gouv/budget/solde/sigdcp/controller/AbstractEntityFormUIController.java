package ci.gouv.budget.solde.sigdcp.controller;

import java.io.Serializable;
import java.util.logging.Level;

import lombok.Getter;
import lombok.extern.java.Log;
import ci.gouv.budget.solde.sigdcp.model.AbstractModel;
import ci.gouv.budget.solde.sigdcp.service.utils.validaton.AbstractValidator;

@Log
public abstract class AbstractEntityFormUIController<ENTITY extends AbstractModel<?>> extends AbstractFormUIController implements Serializable {

	private static final long serialVersionUID = 393104164741887088L;
	
	@Getter protected ENTITY entity;
	
	public AbstractEntityFormUIController() {
		//@SuppressWarnings("unchecked")
		//Class<ENTITY> entityClass = (Class<ENTITY>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}
	
	protected Class<ENTITY> entityClass(){
		return null;
	}
	
	protected AbstractValidator<ENTITY> validator(){
		return null;
	}
	
	@Override
	protected void initCreateOperation() {
		entity = createEntityInstance();
	}
	
	protected ENTITY createEntityInstance(){
		Class<ENTITY> clazz = entityClass();
		if(clazz!=null){
			try {
				return entityClass().newInstance();
			} catch (Exception e) {
				log.log(Level.SEVERE, e.toString(), e);
			}
		}
		return null;
	}
	
	@Override
	protected Boolean valide() {
		AbstractValidator<ENTITY> validator = validator();
		if(validator!=null){
			/*try {
				validator = clazz.newInstance();
			} catch (Exception e) {
				log.log(Level.SEVERE, e.toString(), e);
				addMessageError("Une erreur est survenue lors de la validation des données");
				return Boolean.FALSE;
			}*/
			validator.validate(entity);
			for(String m : validator.getMessages())
				addMessageError(m);
			return validator.isSucces();
		}
		return super.valide();
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
