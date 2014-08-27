package ci.gouv.budget.solde.sigdcp.service.utils;

import java.util.logging.Level;

import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

import lombok.extern.java.Log;

@Interceptor
@TransactionDebugger
@Log
public class TransactionInterceptor {
   
    @AroundInvoke
    public Object runInTransaction(InvocationContext invocationContext) throws Exception {
        Object result = null;
        try {
            result = invocationContext.proceed();
        } catch (Exception e) {
        	//log.log(Level.SEVERE,"Erreur lors d'une transaction.", e);
            throw e;
        }
        return result;
    }
}