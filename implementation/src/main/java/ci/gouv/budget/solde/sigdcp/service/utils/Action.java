package ci.gouv.budget.solde.sigdcp.service.utils;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public abstract class Action implements Serializable {
	
	private static final long serialVersionUID = 5952868522884440964L;

	protected abstract Object __execute__(Object object);
	
	public final Object execute(Object object){
		Object o = __execute__(object);
		return o;
	}
	
}