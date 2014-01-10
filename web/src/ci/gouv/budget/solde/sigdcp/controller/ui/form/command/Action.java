package ci.gouv.budget.solde.sigdcp.controller.ui.form.command;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public abstract class Action implements Serializable {
	
	private static final long serialVersionUID = 5952868522884440964L;

	private Integer executionCount = 0;
	
	protected abstract Object __execute__(Object object) throws Exception;
	
	public final Object execute(Object object) throws Exception {
		Object o = __execute__(object);
		executionCount++;
		return o;
	}
	
	public Boolean isExecutedAtLeastOnce(){
		return executionCount > 0;
	}
	
}