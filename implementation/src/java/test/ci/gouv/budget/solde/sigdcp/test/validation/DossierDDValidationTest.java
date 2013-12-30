package ci.gouv.budget.solde.sigdcp.test.validation;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import ci.gouv.budget.solde.sigdcp.model.dossier.DossierDD;
import ci.gouv.budget.solde.sigdcp.service.utils.validaton.DossierDDValidator;

public class DossierDDValidationTest extends TestCase {
 
	private DossierDD dossier;
    private DossierDDValidator dossierValidator;
    
    @BeforeClass
    public static void oneTimeSetUp() {
        
    }
 
    @AfterClass
    public static void oneTimeTearDown() {
      
    }
 
    @Before
    public void setUp() {
        dossier = new DossierDD();
        dossier.setNumero("D154");
        dossierValidator = new DossierDDValidator();
    }
 
    @After
    public void tearDown() {
        
    }
 
    @Test
    public void testValidationConstraints() {
    	dossierValidator.validate(dossier);
    	for(String message : dossierValidator.getMessages())
    		System.out.println(message);
    	
    	assertTrue(dossierValidator.isSucces());
    }
 
}