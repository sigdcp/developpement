package test.expeval;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import ci.gouv.budget.solde.sigdcp.indemnite.IndemniteOperande;

public class Test2 {
	public static void main(String[] args) throws Exception {
		ScriptEngineManager scriptEngineManager = new ScriptEngineManager();
		ScriptEngine engine = scriptEngineManager.getEngineByName("JavaScript");
		
		String formule = "var resultat;"
				+ "if(masculin){"
				+ "   resultat = op.a+op.b+op.c+op.forfait(false)+op.plage(2);"
				+ "}else{"
				+ "   resultat = 555;"
				+ "}";
		
		engine.put("op", new IndemniteOperande());
		engine.put("masculin", true);
		
		engine.eval(formule);
		
		System.out.println(engine.get("resultat"));
	}
}