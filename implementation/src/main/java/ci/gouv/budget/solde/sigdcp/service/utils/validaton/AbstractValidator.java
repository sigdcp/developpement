package ci.gouv.budget.solde.sigdcp.service.utils.validaton;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import org.apache.commons.lang3.StringUtils;

import lombok.Getter;
import ci.gouv.budget.solde.sigdcp.model.utils.validation.groups.Client;
import ci.gouv.budget.solde.sigdcp.service.utils.ServiceValidationUtils;

public class AbstractValidator<OBJECT> implements Serializable {

	private static final long serialVersionUID = -261860698364195138L;
	
	@Inject protected ServiceValidationUtils validationUtils;	
	
	protected Class<OBJECT> objectClass;
	protected Class<AbstractValidator<OBJECT>> validatorClass;
	//the object to validate
	@Getter protected OBJECT object;
	
	protected List<Class<?>> groups = new LinkedList<>();
	
	// the processor
	protected Validator validator;

	// the results
	@Getter private Set<String> messages;

	public AbstractValidator(Class<OBJECT> objectClass) {
		super();
		constructor(objectClass);
	}
	
	@SuppressWarnings("unchecked")
	public AbstractValidator() {
		super();
		constructor((Class<OBJECT>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0]);
	}
	
	@SuppressWarnings("unchecked")
	private void constructor(Class<OBJECT> objectClass) {
		this.objectClass = objectClass;
		validatorClass = (Class<AbstractValidator<OBJECT>>) this.getClass();
		validator = Validation.buildDefaultValidatorFactory().getValidator();
		groups.add(Client.class);
	}
	
	/*
	public AbstractValidator<OBJECT> init(OBJECT object){
		// initialize fields 
		this.object=object;
		
		return this;
	}*/
	
	public AbstractValidator<OBJECT> validate(OBJECT object){
		this.object=object;
		messages = new LinkedHashSet<>();
		/* processing */
		process(objectClass, object);
		process(validatorClass, this);
		return this;
	}
	
	private <T> void process(Class<T> aClass,T aObject){
		/* bean validation */
		Set<ConstraintViolation<T>> constraintViolationsModel = validator.validate(aObject,groups==null?null:groups.toArray(new Class<?>[]{}));
		
		/* collect messages */
		if(!constraintViolationsModel.isEmpty())
        	for(ConstraintViolation<T> violation : constraintViolationsModel)
        		messages.add(formatMessage(violation));
	}
	
	protected String formatMessage(ConstraintViolation<?> constraintViolation){
		//return constraintViolation.getPropertyPath()+" "+constraintViolation.getMessage();
		return constraintViolation.getMessage();
	}
	
	public Boolean isSucces(){
		return messages==null || messages.isEmpty();
	}
	
	public String getMessagesAsString(){
		return messages==null?"":StringUtils.join(messages, "\r\n");
	}
	
}
