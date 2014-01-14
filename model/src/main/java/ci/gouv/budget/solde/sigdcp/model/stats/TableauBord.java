package ci.gouv.budget.solde.sigdcp.model.stats;

import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedList;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class TableauBord implements Serializable {

	private static final long serialVersionUID = -3138448479124840926L;

	private Collection<TableauBordStatutDetails> statutDetails = new LinkedList<>();
	
	public TableauBord() {}
	
}
