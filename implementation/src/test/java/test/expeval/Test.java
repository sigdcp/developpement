package test.expeval;

import javax.script.Bindings;
import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.SimpleScriptContext;

public class Test {
  public static void main(String[] args) throws Exception{
    ScriptEngineManager mgr = new ScriptEngineManager();
    ScriptEngine engine = mgr.getEngineByName("JavaScript");
    String foo = "40+2*10+3+(j==10?12:25)";
    engine.put("j", 12);
    /*
    ScriptContext newContext = new SimpleScriptContext();
    Bindings engineScope = newContext.getBindings(ScriptContext.ENGINE_SCOPE);
    engineScope.put("j", 12);
    */
    System.out.println(engine.eval("println(j);"));
    System.out.println(engine.eval(foo));
    } 
}