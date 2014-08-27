package ci.gouv.budget.solde.sigdcp.indemnite;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.junit.Assert;
import org.junit.Test;

public class CalculIndemniteUT {

	private ScriptEngineManager scriptEngineManager = new ScriptEngineManager();
	private ScriptEngine engine = scriptEngineManager.getEngineByName("JavaScript");
	
	public CalculIndemniteUT() {
		
	}
	
	@Test
	public void formuleTest() throws ScriptException {
		String formule = "40+2*10+3+(j==10?12:25)";
		engine.put("j", 12);
		int r= ((Double) engine.eval(formule)).intValue();
		Assert.assertEquals(88, r);
	}
		
	@Test
	public void formuleLocalValue() throws ScriptException {
		String formule = "a+b+c";
		engine.put("a", 1);
		engine.put("b", 2);
		engine.put("c", 3);
		int r= ((Double) engine.eval(formule)).intValue();
		Assert.assertEquals(6, r);
	}
	
	@Test
	public void formuleExternalValue() throws ScriptException {
		String formule = "op.a+op.b+op.c+op.forfait(false)";
		engine.put("op", new IndemniteOperande());
		int r= ((Double) engine.eval(formule)).intValue();
		Assert.assertEquals(30, r);
	
	}

}
