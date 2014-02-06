package ci.gouv.budget.solde.sigdcp.service.utils.validaton;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter 
public class ObjectValidator<OBJECT> {
	
	private AbstractValidator<OBJECT> validator;
	private OBJECT object;
	
	public ObjectValidator(OBJECT object,AbstractValidator<OBJECT> validator) {
		super();
		this.validator = validator;
		this.object = object;
	}
	
	public ObjectValidator(OBJECT object) {
		this(object,null);
	}
	
	@SuppressWarnings("unchecked")
	public Boolean validate(){
		if(validator==null)
			validator = (AbstractValidator<OBJECT>) new AbstractValidator<>(object.getClass());
		//validator.init(object).validate();
		validator.validate(object);
		//System.out.println("Validating : "+object+" Success : "+validator.isSucces());
		return validator.isSucces();
	}

	@Override
	public String toString() {
		return object.getClass()+" Validator";
	}
	
}
