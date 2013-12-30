package ci.gouv.budget.solde.sigdcp.test.validation;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import ci.gouv.budget.solde.sigdcp.model.dossier.DossierTR;

public class OrderedDateTest extends TestCase {
 
	private Validator validator;
    private DossierTR dossierTR;
    
    @BeforeClass
    public static void oneTimeSetUp() {
        
    }
 
    @AfterClass
    public static void oneTimeTearDown() {
      
    }
 
    @Before
    public void setUp() {
        dossierTR = new DossierTR();
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }
 
    @After
    public void tearDown() {
        
    }
 
    @Test
    public void testValidationConstraints() {
    	Set<ConstraintViolation<DossierTR>> constraintViolations = validator.validate(dossierTR);
        if(!constraintViolations.isEmpty())
        	for(ConstraintViolation<DossierTR> violation : constraintViolations)
        		System.out.println(violation.getPropertyPath()+" "+violation.getMessage());
        	
    	assertTrue(constraintViolations.isEmpty());
    }
 
}