package ci.gouv.budget.solde.sigdcp.controller.flow;

import java.io.Serializable;

import org.primefaces.component.commandbutton.CommandButton;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class FlowActions implements Serializable {

	private CommandButton previous;
	private CommandButton next;
	private CommandButton cancel;
	
}
