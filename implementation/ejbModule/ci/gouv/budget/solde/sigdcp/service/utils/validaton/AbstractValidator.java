package ci.gouv.budget.solde.sigdcp.service.utils.validaton;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import lombok.Getter;

public class AbstractValidator<OBJECT> implements Serializable {

	private static final long serialVersionUID = -261860698364195138L;
	
	protected Class<OBJECT> objectClass;
	protected Class<AbstractValidator<OBJECT>> validatorClass;
	//the object to validate
	@Getter protected OBJECT object;
	
	// the processor
	private Validator validator;
	
	// the results
	@Getter private Set<String> messages;

	@SuppressWarnings("unchecked")
	public AbstractValidator() {
		super();
		objectClass = (Class<OBJECT>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
		validatorClass = (Class<AbstractValidator<OBJECT>>) this.getClass();
		validator = Validation.buildDefaultValidatorFactory().getValidator();
	}
	
	public void validate(OBJECT object){
		/* initialize fields */
		this.object=object;
		messages = new LinkedHashSet<>();
		
		/* processing */
		process(objectClass, object);
		process(validatorClass, this);
	}
	
	private <T> void process(Class<T> aClass,T aObject){
		/* bean validation */
		Set<ConstraintViolation<T>> constraintViolationsModel = validator.validate(aObject);
		
		/* collect messages */
		if(!constraintViolationsModel.isEmpty())
        	for(ConstraintViolation<T> violation : constraintViolationsModel)
        		messages.add(formatMessage(violation));
	}
	
	protected String formatMessage(ConstraintViolation<?> constraintViolation){
		return constraintViolation.getMessage();
	}
	
	public Boolean isSucces(){
		return messages==null || messages.isEmpty();
	}
	
}
