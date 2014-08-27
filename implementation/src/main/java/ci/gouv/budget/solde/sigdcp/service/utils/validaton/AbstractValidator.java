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
import lombok.Setter;
import ci.gouv.budget.solde.sigdcp.model.utils.validation.groups.Client;
import ci.gouv.budget.solde.sigdcp.service.ServiceException;
import ci.gouv.budget.solde.sigdcp.service.ServiceExceptionNoRollBack;
import ci.gouv.budget.solde.sigdcp.service.ServiceExceptionType;

/**
 * Ensemble de methodes automatiquement appele par le conteneur pour la validation de contraintes.<br/>
 * La signature doit avoir le format suivant : isValidXXX ou XXX est le nom de l'attribut ou de la contrainte. ex : isValidDateNaissance , 
 * is ValidMajorite , isValidDistance  , etc.
 * 
 * @author Komenan Y .Christian
 *
 * @param <OBJECT>
 */
public class AbstractValidator<OBJECT> implements Serializable {

	private static final long serialVersionUID = -261860698364195138L;
	
	protected static final String MESSAGE_NOT_VALID_FORMAT = "%s n'est pas valide";
	
	@Inject protected ValidationPolicy validationPolicy;	
	
	protected Class<OBJECT> objectClass;
	protected Class<AbstractValidator<OBJECT>> validatorClass;
	//the object to validate
	@Getter protected OBJECT object;
	
	protected List<Class<?>> groups = new LinkedList<>();
	
	// the processor
	protected Validator validator;

	// the results
	@Getter protected Set<String> messages = new LinkedHashSet<>();
	@Getter @Setter private Boolean autoClearMessages=Boolean.TRUE;

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
		if(Boolean.TRUE.equals(autoClearMessages))
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
	
	public void manualProcess(){
		
	}
	
	protected String formatMessage(ConstraintViolation<?> constraintViolation){
		//return constraintViolation.getPropertyPath()+" "+constraintViolation.getMessage();//TODO just for test! Do use the next line
		return constraintViolation.getMessage();
	}
	
	public Boolean isSucces(){
		return messages==null || messages.isEmpty();
	}
	
	public String getMessagesAsString(){
		return messages==null?"":StringUtils.join(messages, "\r\n");
	}
	
	protected static String messageNotValid(String constraint){
		return String.format(MESSAGE_NOT_VALID_FORMAT, constraint);
	}
	
	/**/
	
	protected static void serviceException(ServiceExceptionType type,Boolean rollback){
		serviceException(type.getLibelle(),rollback);
	}

	protected static void serviceException(ServiceExceptionType type){
		serviceException(type, Boolean.TRUE);
	}
	
	protected static void serviceException(String message,Boolean rollback){
		//System.out.println("Rollback : "+rollback);
		if(rollback)
			throw new ServiceException(message);
		//System.out.println("DefaultServiceImpl.serviceException()");
		throw new ServiceExceptionNoRollBack(message);
	}
	protected static void serviceException(String message){
		serviceException(message, Boolean.TRUE);
	}
	
}
