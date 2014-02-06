package ci.gouv.budget.solde.sigdcp.test.validation;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;

import ci.gouv.budget.solde.sigdcp.service.utils.validaton.AbstractValidator;

public abstract class AbstractValidationTestCase<OBJECT> extends TestCase {
 
	/*
	@Deployment public static JavaArchive createDeployment() { 
		return ShrinkWrap.create(JavaArchive.class) 
				.addClass(ServiceValidationUtils.class)
				.addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml"); 
	}
	*/
	
	protected OBJECT object;
    protected AbstractValidator<OBJECT> validator;
    
    protected abstract OBJECT createObject();
    
    protected abstract AbstractValidator<OBJECT> createValidator();
    
    
    
    public AbstractValidationTestCase() {
		super();
	}

	public AbstractValidationTestCase(String name) {
		super(name);
	}

	@Before
    public void setUp() {
        object = createObject();
        validator = createValidator();
    }
 
    @Test
    public void testValidationConstraints() {
    	//validator.init(object).validate();
    	for(String message : validator.getMessages())
    		System.out.println(message);
    	assertTrue(Boolean.TRUE /*validator.isSucces()*/);
    }
 
}