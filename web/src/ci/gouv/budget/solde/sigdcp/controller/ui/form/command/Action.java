package ci.gouv.budget.solde.sigdcp.controller.ui.form.command;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public abstract class Action implements Serializable {
	
	private static final long serialVersionUID = 5952868522884440964L;

	private Integer executionCount = 0;
	
	protected abstract void __execute__() throws Exception;
	
	public final void execute() throws Exception {
		__execute__();
		executionCount++;
	}
	
	public Boolean isExecutedAtLeastOnce(){
		return executionCount > 0;
	}
	
}