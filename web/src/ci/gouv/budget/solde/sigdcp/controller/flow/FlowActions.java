package ci.gouv.budget.solde.sigdcp.controller.flow;

import java.io.Serializable;

import org.primefaces.component.commandbutton.CommandButton;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class FlowActions implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -460049368577766000L;
	private CommandButton previous;
	private CommandButton next;
	private CommandButton cancel;
	
}
