package ci.gouv.budget.solde.sigdcp.controller.ui.form;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.logging.Level;

import javax.validation.Valid;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;
import ci.gouv.budget.solde.sigdcp.controller.ui.form.command.FormCommand;
import ci.gouv.budget.solde.sigdcp.model.AbstractModel;
import ci.gouv.budget.solde.sigdcp.service.utils.validaton.AbstractValidator;
import ci.gouv.budget.solde.sigdcp.service.utils.validaton.ObjectValidator;

@Log
public abstract class AbstractEntityFormUIController<ENTITY extends AbstractModel<?>> extends AbstractFormUIController<ENTITY> implements Serializable {

	private static final long serialVersionUID = 393104164741887088L;
	
	protected Class<ENTITY> entityClass;
	
	@Valid
	@Getter @Setter protected ENTITY entity;
	
	@SuppressWarnings("unchecked")
	public AbstractEntityFormUIController() {
		if(getClass().getGenericSuperclass() instanceof ParameterizedType)
			entityClass = (Class<ENTITY>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}

	@Override
	protected void afterInitialisation() {
		super.afterInitialisation();
		addValidator(validator());
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
	
	protected AbstractValidator<ENTITY> validator(){
		return null;
	}
	
	protected void addValidator(AbstractValidator<ENTITY> validator,FormCommand<ENTITY> command){
		if(validator!=null)
			command.getObjectValidators().add(new ObjectValidator<ENTITY>(entity, validator));
	}
	
	protected void addValidator(AbstractValidator<ENTITY> validator){
		addValidator(validator, defaultSubmitCommand);
	}
	
	@Override
	public ENTITY getDto() {
		return entity;
	}

}
